package com.kokonetworks.theapp.improved

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import com.kokonetworks.theapp.R
import com.kokonetworks.theapp.databinding.ActivityImprovedBinding
import java.lang.reflect.Field

class ImprovedGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImprovedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImprovedBinding.inflate(layoutInflater).apply {
            field.apply {
                callback = object : GameCallback {
                    override fun onGameEnded(score: Int) {
                        btnStart.isVisible = true
                        tvScore.isVisible = true
                        tvScore.text = String.format(getString(R.string.your_score), score)
                    }

                    override fun onLevelChange(level: Int) {
                        tvLevel.text = String.format(getString(R.string.level), level)
                    }
                }
            }

            btnStart.setOnClickListener {
                it.isVisible = false
                tvScore.isVisible = false
                field.startGame()
            }
        }
        setContentView(binding.root)
    }
}