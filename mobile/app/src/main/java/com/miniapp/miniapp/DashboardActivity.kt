package com.miniapp.miniapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // Retrieve the stored JWT token
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("JWT_TOKEN", null)

        // Basic Protection: If no token, kick user back to Login
        if (token == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        tvWelcome.text = "Welcome to your Protected Dashboard!"

        btnLogout.setOnClickListener {
            // 1. Clear the token (Requirement 1.4: Logout functionality)
            sharedPref.edit().remove("JWT_TOKEN").apply()

            // 2. Redirect to Login and clear activity stack
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}