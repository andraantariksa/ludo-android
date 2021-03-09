package io.github.andraantariksa.ludo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.andraantariksa.ludo.databinding.ActivityMainBinding
import io.github.andraantariksa.ludo.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {
    private var binding: ActivityMainMenuBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        setupListener()
    }

    private fun setupListener() {
        binding?.buttonPlay?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding?.buttonExit?.setOnClickListener {
            finishAffinity()
        }
    }
}