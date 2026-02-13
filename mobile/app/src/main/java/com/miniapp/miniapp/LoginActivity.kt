package com.miniapp.miniapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.miniapp.miniapp.network.RetrofitClient
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // CRITICAL: This links the UI to the code

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.instance.login(mapOf(
                        "email" to username,
                        "password" to password
                    ))

                    if (response.isSuccessful) {
                        val token = response.body()?.get("token")
                        getSharedPreferences("AppPrefs", MODE_PRIVATE).edit()
                            .putString("JWT_TOKEN", token).apply()

                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        finish() // Prevent going back to Login with the back button
                    } else {
                        Toast.makeText(this@LoginActivity, "Login Failed: Invalid Credentials", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    // This catches network errors (like the server being down)
                    Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}