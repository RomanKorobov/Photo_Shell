package com.example.photoshell

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.photoshell.data.AuthConfig
import com.example.photoshell.data.TokenStorage
import com.example.photoshell.databinding.ActivityMainBinding
import com.example.photoshell.ui.MainFragment
import com.example.photoshell.ui.OnBoardingAdapter
import com.example.photoshell.ui.OnBoardingScreen
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.GrantTypeValues
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenRequest

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding()
    private val serviceConfig = AuthorizationServiceConfiguration(
        Uri.parse(AuthConfig.AUTHORIZE_URI),
        Uri.parse(AuthConfig.TOKEN_URI)
    )
    private val authService: AuthorizationService by lazy { AuthorizationService(this) }
    private val authRequest = AuthorizationRequest.Builder(
        serviceConfig,
        AuthConfig.ACCESS_KEY,
        ResponseTypeValues.CODE,
        Uri.parse(AuthConfig.REDIRECT_URI)
    ).setScope(AuthConfig.SCOPE).build()
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.viewPager)
        val adapter = OnBoardingAdapter(this)
        adapter.addFragment(
            OnBoardingScreen(
                R.string.onboarding_one,
                R.color.teal200,
                R.drawable.onboarding_one
            )
        )
        adapter.addFragment(
            OnBoardingScreen(
                R.string.onboarding_two,
                R.color.green_a400,
                R.drawable.onboarding_two
            )
        )
        adapter.addFragment(
            OnBoardingScreen(
                R.string.onboarding_three,
                R.color.blue_grey_100,
                R.drawable.onboarding_three
            )
        )
        viewPager.adapter = adapter
        val indicator: com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator = findViewById(R.id.spring_dots_indicator)
        indicator.setViewPager2(viewPager)
        if (intent.data != null) {
            val code = Uri.parse(intent.data.toString()).getQueryParameter("code")
            val clientAuth: ClientAuthentication = ClientSecretBasic(AuthConfig.SECRET_KEY)
            authService.performTokenRequest(
                TokenRequest.Builder(serviceConfig, AuthConfig.ACCESS_KEY)
                    .setAuthorizationCode(code).setRedirectUri(Uri.parse(AuthConfig.REDIRECT_URI))
                    .setGrantType(GrantTypeValues.AUTHORIZATION_CODE).build(), clientAuth
            ) { response, exception ->
                if (response != null) {
                    val accessToken = response.accessToken
                    if (accessToken != null) {
                        TokenStorage.saveToken(this, accessToken)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, MainFragment()).commit()
                    }
                }
                exception?.printStackTrace()
            }
        }
        binding.loginButton.setOnClickListener { loginStart() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selected_position", viewPager.currentItem)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        val position = savedInstanceState?.getInt("selected_position")
        if (position != null) {
            viewPager.currentItem = position
        }
    }

    private fun loginStart() {
        try {
            if (TokenStorage.getToken(this) != null) {
                supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment())
                    .commit()
            }
        } catch (n: NullPointerException) {
            authService.performAuthorizationRequest(
                authRequest,
                PendingIntent.getActivity(
                    this,
                    0,
                    Intent(this, MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
        }
    }
}