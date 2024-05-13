package com.example.photoshell.ui.start

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.photoshell.R
import com.example.photoshell.data.KeyStoreManager
import com.example.photoshell.databinding.FragmentStartBinding
import com.example.photoshell.ui.MainFragment

class StartFragment : Fragment(R.layout.fragment_start) {
    private val binding: FragmentStartBinding by viewBinding()
    private val viewModel: StartViewModel by viewModels()
    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIntent()
        viewPager = binding.viewPager
        val adapter = OnBoardingAdapter(requireActivity())
        adapter.addFragment(
            OnBoardingScreen(
                R.string.onboarding_one,
                R.color.teal200,
                R.drawable.generated_topic
            )
        )
        adapter.addFragment(
            OnBoardingScreen(
                R.string.onboarding_two,
                R.color.green_a400,
                R.drawable.save_onboarding
            )
        )
        adapter.addFragment(
            OnBoardingScreen(
                R.string.onboarding_three,
                R.color.blue_grey_100,
                R.drawable.surf_onboarding
            )
        )
        viewPager.adapter = adapter
        val indicator: com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator =
            binding.springDotsIndicator
        indicator.setViewPager2(viewPager)
        binding.loginButton.setOnClickListener { checkIfLoggedIn() }
    }

    private fun checkIfLoggedIn() {
        if (KeyStoreManager.get(requireContext()) != null) {
            parentFragmentManager.beginTransaction().replace(R.id.container, MainFragment())
                .commit()
        } else {
            viewModel.performAuthorizationRequest()
        }
    }

    private fun checkIntent() {
        val dataStr = arguments?.getString("intent")
        val logout = arguments?.getBoolean("logout")
        if (logout == true) return
        if (dataStr != null) {
            val code = Uri.parse(dataStr).getQueryParameter("code") ?: ""
            viewModel.performTokenRequest(code)
            parentFragmentManager.beginTransaction().replace(R.id.container, MainFragment())
                .commit()
        }
    }
}