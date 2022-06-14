package com.example.projet_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    lateinit var gameView: GameView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameView = findViewById<GameView>(R.id.surfaceView)
    }
    override fun onPause() {
        super.onPause()
        gameView.pause()
    }
    override fun onResume() {
        super.onResume()
        gameView.resume()
    }
}