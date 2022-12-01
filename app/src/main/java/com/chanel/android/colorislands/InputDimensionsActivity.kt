package com.chanel.android.colorislands

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chanel.android.colorislands.databinding.ActivityInputDimensionsBinding

class InputDimensionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputDimensionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputDimensionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enterBtn.setOnClickListener {
            val numRows = binding.inputRows.text.toString().toIntOrNull()
            val numColumns = binding.inputColumns.text.toString().toIntOrNull()
            val intent = ColorIslandsActivity.newIntent(this@InputDimensionsActivity, numRows, numColumns)
            startActivity(intent)
        }
    }
}