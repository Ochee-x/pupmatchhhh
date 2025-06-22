package com.example.pupmatch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third) // Ensure this layout exists

        val getStartedButton: Button = findViewById(R.id.get_started_button) // Ensure this ID exists in activity_third.xml
        getStartedButton.setOnClickListener {
            // Define what happens when the button is clicked
            // For example, you can navigate to another activity or perform an action
            val intent = Intent(this, Login ::class.java) // Replace AnotherActivity with your target activity
            startActivity(intent)
        }
    }
}
