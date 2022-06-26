package org.hacdan.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlin.math.round
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnCalculate = findViewById<Button>(R.id.btnCalculate)
        var tvTipAmount = findViewById<TextView>(R.id.tvTipAmount)

        btnCalculate.setOnClickListener{
            var tip = calculateTip()
            """${"$"}${tip}""".also { tvTipAmount.text = it }
        }
    }

    private fun calculateTip(): Double {
        var rgTipOptions = findViewById<RadioGroup>(R.id.rgTipOptions)
        var etCostOfService = findViewById<EditText>(R.id.etCostOfService)

        var tipPercent = 0.0

        if (etCostOfService.text.isEmpty()) {
            Toast.makeText(this@MainActivity, "Please enter a cost of service before caclulating", Toast.LENGTH_SHORT).show()
            return 0.0
        }
        if (rgTipOptions.checkedRadioButtonId == -1){
            Toast.makeText(this@MainActivity, "Please be sure to select how the service was.", Toast.LENGTH_SHORT).show()
            return 0.0
        }
        when(rgTipOptions.checkedRadioButtonId) {
            R.id.rbAmazing -> tipPercent = 0.2
            R.id.rbGood -> tipPercent = 0.18
            R.id.rbOkay -> tipPercent = 0.15
        }
        var tip = etCostOfService.text.toString().toDouble() * tipPercent
        // TODO: Update rounding to use textFormat as opposed to roundToInt
        // TODO: Implement Round up tip option
        return (tip * 100.0).roundToInt() / 100.0
    }
}
