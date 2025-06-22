package com.example.pupmatch

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SecondActivityy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_activityy) // Ensure this layout exists

        // Find the arrow ImageView in the layout
        val arrow: ImageView = findViewById(R.id.arrow) // Ensure this ID exists in activity_second_activityy.xml
        arrow.setOnClickListener {
            // Navigate to ThirdActivity when the arrow is clicked
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }
}
