package com.example.pupmatch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class Signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val allowedDomain = "@iskolarngbayan.pup.edu.ph"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup_page)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Set up window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        val emailInput: EditText = findViewById(R.id.editTextTextEmailAddress)
        val passwordInput: EditText = findViewById(R.id.textInputEditText2)
        val confirmPasswordInput: EditText = findViewById(R.id.textInputEditText5)
        val signupButton: Button = findViewById(R.id.signupbtn)
        val loginText: TextView = findViewById(R.id.textView6)
        val termsCheckbox: CheckBox = findViewById(R.id.checkBox)

        // Set up signup button click listener
        signupButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            // Check if the email ends with the allowed domain
            if (!email.endsWith(allowedDomain)) {
                Toast.makeText(
                    this,
                    "Please use a valid school email address.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(
                    this,
                    "Password must be at least 6 characters long",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if the terms checkbox is checked
            if (!termsCheckbox.isChecked) {
                Toast.makeText(
                    this,
                    "You must agree to the terms and conditions",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Create user with Firebase
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()
                        // Navigate to Name Activity after successful signup
                        val intent = Intent(this, Name::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Signup failed: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        // Navigate back to Login Activity
        loginText.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}
