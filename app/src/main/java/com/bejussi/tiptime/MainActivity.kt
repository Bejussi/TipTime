package com.bejussi.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.bejussi.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }

        binding.costOfService.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }
    }

        private fun calculateTip() {
            val stringCostInTextField = binding.costOfService.text.toString()
            val cost =  stringCostInTextField.toDoubleOrNull()
            if (cost == null) {
                binding.tipResult.text = getString(R.string.null_text)
                binding.totalResult.text = getString(R.string.null_text)
                binding.perPersonResult.text = getString(R.string.null_text)
                return
            }

            val stringPersonInTextField = binding.numberOfPeople.text.toString()
            val person = stringPersonInTextField.toDoubleOrNull()
            if(person == null) {

            }


            val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
                R.id.option_zero_percent -> 0.0
                R.id.option_ten_percent -> 0.1
                R.id.option_fifteen_percent -> 0.15
                else -> 0.2
            }

            var tip = tipPercentage * cost
            if (binding.roundUpSwitch.isChecked) {
                tip = kotlin.math.ceil(tip)
            }

            //val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
            binding.tipResult.text = formatPrice(tip)

            val total = cost + tip
            //val formattedTotal = NumberFormat.getCurrencyInstance().format(total)
            binding.totalResult.text = formatPrice(total)

            val perPerson = total / person!!
            //val formattedRerPerson = NumberFormat.getCurrencyInstance().format(perPerson)
            binding.perPersonResult.text = formatPrice(perPerson)

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

    private fun formatPrice(price: Double): String {
        return "%.1f".format(price)
    }
}


