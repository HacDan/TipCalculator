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

        val btnCalculate = findViewById<Button>(R.id.btn_calculate)
        val tvTipAmount = findViewById<TextView>(R.id.tv_tip_amount)
        val etCostOfService = findViewById<EditText>(R.id.et_cost_of_service)
        val tvTotal = findViewById<TextView>(R.id.tv_total)
        val rgTipOptions = findViewById<RadioGroup>(R.id.rg_tip_options)

        btnCalculate.setOnClickListener{
            if (etCostOfService.text.isEmpty()) {
                Toast.makeText(this@MainActivity, "Please enter a cost of service before calculating", Toast.LENGTH_SHORT).show()
            } else if (rgTipOptions.checkedRadioButtonId == -1){
                Toast.makeText(this@MainActivity, "Please be sure to select how the service was.", Toast.LENGTH_SHORT).show()
            } else {
                val costOfService = etCostOfService.text.toString().toDouble()
                val tip = calculateTip()
                val total = costOfService + tip

                """${"$"}${
                    String.format(
                        "%.2f",
                        tip
                    )
                }""".also { tvTipAmount.text = it }
                """${"$"}${
                    String.format(
                        "%.2f",
                        total
                    )
                }""".also { tvTotal.text = it }
            }
        }
    }

    private fun calculateTip(): Double {
        val rgTipOptions = findViewById<RadioGroup>(R.id.rg_tip_options)
        val etCostOfService = findViewById<EditText>(R.id.et_cost_of_service)
        val swRoundTip = findViewById<SwitchCompat>(R.id.sw_round_up)
        val roundTip = swRoundTip.isChecked

        var tipPercent = 0.0

        when(rgTipOptions.checkedRadioButtonId) {
            R.id.rb_amazing -> tipPercent = 0.2
            R.id.rb_good -> tipPercent = 0.18
            R.id.rb_okay -> tipPercent = 0.15
        }
        val tip = etCostOfService.text.toString().toDouble() * tipPercent

        if (roundTip) {
            return kotlin.math.ceil(tip)
        }
        return tip
    }
}
