package com.example.photoshell.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.photoshell.R

class OnBoardingScreen() : Fragment() {
    constructor(
        @StringRes textRes: Int,
        @ColorRes colorRes: Int,
        @DrawableRes drawableRes: Int
    ) : this() {
        this.textRes = textRes
        this.colorRes = colorRes
        this.drawableRes = drawableRes
    }

    private lateinit var onBoardingTextView: TextView
    private lateinit var onBoardingImageView: ImageView

    private var textRes: Int = R.string.default_text_res
    private var colorRes: Int = R.color.default_color_res
    private var drawableRes: Int = R.drawable.default_drawable_res

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBoardingTextView = view.findViewById(R.id.onBoardingTextView)
        onBoardingImageView = view.findViewById(R.id.onBoardingImageView)

        onBoardingTextView.setBackgroundResource(colorRes)
        onBoardingTextView.text = getString(textRes)
        onBoardingImageView.setImageResource(drawableRes)
    }
}
