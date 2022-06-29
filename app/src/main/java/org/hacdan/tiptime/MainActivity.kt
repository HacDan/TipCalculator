package org.hacdan.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import org.hacdan.tiptime.databinding.ActivityMainBinding
import kotlin.String

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "Entered onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Hide keyboard once editText loses focus

        binding.btnCalculate.setOnClickListener{
            if (binding.etCostOfService.text?.isEmpty() as Boolean) {
                Toast.makeText(this@MainActivity, "Please enter a cost of service before calculating", Toast.LENGTH_SHORT).show()
                Log.v(TAG, "No cost of service entered")
            } else if (binding.rgTipOptions.checkedRadioButtonId == -1){
                Toast.makeText(this@MainActivity, "Please be sure to select how the service was.", Toast.LENGTH_SHORT).show()
                Log.v(TAG, "No tip percentage was entered")
            } else {
                val costOfService = binding.etCostOfService.text.toString().toDouble()
                val tip = calculateTip()
                Log.v(TAG, "Tip amount: $tip")

                val total = costOfService + tip
                Log.v(TAG, "Tip plus service cost: $total")

                """${"$"}${
                    String.format(
                        "%.2f",
                        tip
                    )
                }""".also { binding.tvTipAmount.text = it }
                """${"$"}${
                    String.format(
                        "%.2f",
                        total
                    )
                }""".also { binding.tvTotal.text = it }
            }
        }
    }

    private fun calculateTip(): Double {
        var tipPercent = 0.0

        when(binding.rgTipOptions.checkedRadioButtonId) {
            R.id.rb_amazing -> tipPercent = 0.2
            R.id.rb_good -> tipPercent = 0.18
            R.id.rb_okay -> tipPercent = 0.15
        }
        val tip = binding.etCostOfService.text.toString().toDouble() * tipPercent

        if (binding.swRoundUp.isChecked) {
            return kotlin.math.ceil(tip)
        }
        return tip
    }
}
