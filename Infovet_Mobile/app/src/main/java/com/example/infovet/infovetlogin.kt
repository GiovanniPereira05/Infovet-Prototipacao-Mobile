package com.example.infovet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class infovetlogin : AppCompatActivity() {
    private lateinit var buttonLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_infovetlogin)
        buttonLogin = findViewById(R.id.buttonEntrar)

        buttonLogin.setOnClickListener {
            val intent = Intent(this, TelaInicial::class.java)
            startActivity(intent)
        }

    }
}