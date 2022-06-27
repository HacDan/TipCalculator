package org.hacdan.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import kotlin.String

// TODO: Convert to View Bindings
// TODO: Add logging

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val tvTipAmount = findViewById<TextView>(R.id.tvTipAmount)
        val etCostOfService = findViewById<EditText>(R.id.etCostOfService)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)

        btnCalculate.setOnClickListener{
            val costOfService = etCostOfService.text.toString().toDouble()
            val tip = calculateTip()
            val total = costOfService + tip

            """${"$"}${String.format("%.2f",tip)}""".also { tvTipAmount.text = it }
            """${"$"}${String.format("%.2f",total)}""".also { tvTotal.text = it }
        }
    }

    private fun calculateTip(): Double {
        val rgTipOptions = findViewById<RadioGroup>(R.id.rgTipOptions)
        val etCostOfService = findViewById<EditText>(R.id.etCostOfService)
        val swRoundTip = findViewById<SwitchCompat>(R.id.swRoundUp)
        val roundTip = swRoundTip.isChecked

        var tipPercent = 0.0

        if (etCostOfService.text.isEmpty()) {
            Toast.makeText(this@MainActivity, "Please enter a cost of service before calculating", Toast.LENGTH_SHORT).show()
            return 0.00
        }
        if (rgTipOptions.checkedRadioButtonId == -1){
            Toast.makeText(this@MainActivity, "Please be sure to select how the service was.", Toast.LENGTH_SHORT).show()
            return 0.00
        }
        when(rgTipOptions.checkedRadioButtonId) {
            R.id.rbAmazing -> tipPercent = 0.2
            R.id.rbGood -> tipPercent = 0.18
            R.id.rbOkay -> tipPercent = 0.15
        }
        var tip = etCostOfService.text.toString().toDouble() * tipPercent
        if (roundTip) {
            tip = kotlin.math.ceil(tip)
        }
        return tip
    }
}
