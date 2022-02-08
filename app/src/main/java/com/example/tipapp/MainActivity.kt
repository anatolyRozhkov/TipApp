package com.example.tipapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tipapp.databinding.ActivityMainBinding
import java.text.NumberFormat
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("MyActivity", "App started!")

        binding.largeButton.setOnClickListener { calculateTip() }
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }

    }

    private fun calculateTip() {
        // get input text
        // check if input is not empty
        if (binding.costOfServiceEditText.text.toString() == "") {
            binding.displayAnswer.text = ""
            Log.d("MyActivity", "Empty input")
        } else {
            val cost = binding.costOfServiceEditText.text.toString().toDouble()
            Log.d("MyActivity", "Input: $cost")

            // convert radio button id to percentage
            val tipPercentage = when (binding.radioGroup.checkedRadioButtonId) {
                R.id.first -> 0.20
                R.id.second -> 0.18
                else -> 0.15
            }

            // calculate tip
            var tip = tipPercentage * cost

            // round up the tip if switch is checked
            if (binding.switchToggle.isChecked) {
                tip = kotlin.math.ceil(tip)
            }

            // format tip to currency
            val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
            Log.d("MyActivity", "Formatted string: $formattedTip")

            // display output
            binding.displayAnswer.text = getString(R.string.display_tip, formattedTip)
        }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}