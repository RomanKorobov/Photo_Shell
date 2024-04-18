package com.example.photoshell

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photoshell.ui.start.StartFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (intent != null) {
            val bundle = Bundle()
            bundle.putString("intent", intent.dataString)
            val newFragment = StartFragment()
            newFragment.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.container, newFragment).commit()
        }
    }
}