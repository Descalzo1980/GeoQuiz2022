package ru.stas.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import ru.stas.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener {
            Snackbar.make(
                binding.root,
                R.string.correct_toast,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        binding.falseButton.setOnClickListener { view : View ->
            Toast.makeText(applicationContext,R.string.incorrect_toast,Toast.LENGTH_SHORT).show()
        }
    }
}