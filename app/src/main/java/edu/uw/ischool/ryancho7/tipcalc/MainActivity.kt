package edu.uw.ischool.ryancho7.tipcalc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    private lateinit var editText : EditText
    private lateinit var tipButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editText = findViewById(R.id.editText)
        tipButton = findViewById(R.id.calculateTipButton)

        editText.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // s represents what is in the text field so check if it is empty -> not empty means button enabled
                tipButton.isEnabled = !s.isNullOrEmpty()
            }
        })

        tipButton.setOnClickListener {
            calculateTip()
        }

    }

    private fun calculateTip() {
        val amount = editText.text.toString()
        val doubleAmount = amount.toDoubleOrNull()
        // only perform the operation if we are working with a non-null amount
        if(doubleAmount != null) {
            val tip = doubleAmount * 0.15
            // if we have a tip that has more than two decimal places
            val roundedTip = BigDecimal(tip).setScale(2, RoundingMode.HALF_EVEN).toString()
            val formattedTip = String.format("$%s", roundedTip)
            Toast.makeText(this, "Tip: $formattedTip", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Invalid tip amount", Toast.LENGTH_SHORT).show()
        }
    }
}