package ru.stas.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import ru.stas.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questionsBank = listOf(
        Questions(R.string.question_australia, true),
        Questions(R.string.question_oceans, true),
        Questions(R.string.question_mideast, false),
        Questions(R.string.question_africa, false),
        Questions(R.string.question_americas, true),
        Questions(R.string.question_asia, true))

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateQuestion()

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }
        binding.falseButton.setOnClickListener {
          checkAnswer(false)
        }
        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionsBank.size
            updateQuestion()
        }
    }
    private fun updateQuestion(){
        val questionResId = questionsBank[currentIndex].textResId
        binding.questionTextView.setText(questionResId)
    }

    private fun checkAnswer(userAnswer : Boolean){
        val correctAnswer = questionsBank[currentIndex].answer
        val messageResId =
            if(userAnswer == correctAnswer){
                R.string.correct_toast
            }else{
                R.string.incorrect_toast
            }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show()
    }
}