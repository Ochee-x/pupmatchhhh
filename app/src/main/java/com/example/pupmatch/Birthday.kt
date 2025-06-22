package com.example.pupmatch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class Birthday : AppCompatActivity() {

    private lateinit var daySpinner: Spinner
    private lateinit var monthSpinner: Spinner
    private lateinit var yearSpinner: Spinner
    private lateinit var confirmButton: Button
    private lateinit var selectedDateText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_birthday)

        // Initialize views
        daySpinner = findViewById(R.id.daySpinner)
        monthSpinner = findViewById(R.id.monthSpinner)
        yearSpinner = findViewById(R.id.yearSpinner)
        confirmButton = findViewById(R.id.confirmButton)
        selectedDateText = findViewById(R.id.selectedDateText)

        // Setup edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
        val gender: Button = findViewById(R.id.confirmButton)
        gender.setOnClickListener {
            // Navigate to Birthday Activity
            val intent = Intent(this, Gender::class.java)
            startActivity(intent)
        }

        // Setup date pickers
        setupDatePickers()

        // Set up confirm button click listener
        confirmButton.setOnClickListener {
            //showSelectedDate()
        }
    }

    private fun setupDatePickers() {
        // Setup months
        val months = listOf(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
        )

        monthSpinner.adapter = createSpinnerAdapter(months)
        monthSpinner.setSelection(Calendar.getInstance().get(Calendar.MONTH))

        // Setup years (current ±50 years)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (currentYear - 50..currentYear + 10).toList()

        yearSpinner.adapter = createSpinnerAdapter(years.map { it.toString() })
        yearSpinner.setSelection(years.indexOf(currentYear))

        // Setup days (will update when month/year changes)
        updateDaySpinner()

        // Set listeners for month/year changes
        monthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateDaySpinner()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateDaySpinner()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun updateDaySpinner() {
        val month = monthSpinner.selectedItemPosition
        val year = yearSpinner.selectedItem.toString().toInt()

        val calendar = Calendar.getInstance().apply {
            set(year, month, 1)
        }
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Format days with leading zero (01, 02, etc.)
        val days = (1..daysInMonth).map {
            String.format("%02d", it)
        }

        daySpinner.adapter = createSpinnerAdapter(days)

        // Set selection to first day if current day isn't available
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        daySpinner.setSelection(if (currentDay <= daysInMonth) currentDay - 1 else 0)
    }

    private fun createSpinnerAdapter(items: List<String>): ArrayAdapter<String> {
        return ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            items
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

   /* private fun showSelectedDate() {
        val day = daySpinner.selectedItem.toString()
        val month = monthSpinner.selectedItemPosition + 1 // Convert to 1-12
        val year = yearSpinner.selectedItem.toString()

        val formattedDate = "$day/${String.format("%02d", month)}/$year"
        selectedDateText.text = "Selected: $formattedDate"

        Toast.makeText(
            this,
            "Birthday set to $formattedDate",
            Toast.LENGTH_SHORT
        ).show()


    }*/

}
