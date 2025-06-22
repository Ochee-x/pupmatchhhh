package com.example.pupmatch

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Ensure this layout exists

        FirebaseApp.initializeApp(this)

        val arrow: ImageView = findViewById(R.id.arrow) // Ensure this ID exists in activity_main.xml
        arrow.setOnClickListener {
            // Navigate to the SecondActivity
            val intent = Intent(this, SecondActivityy::class.java)
            startActivity(intent)
        }
    }
}

