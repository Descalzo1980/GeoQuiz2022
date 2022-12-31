package ru.stas.geoquiz

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.stas.geoquiz.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var questionsBank = listOf(
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
            resetState()
            nextQuestion()
            updateQuestion()

        }
        binding.questionTextView.setOnClickListener {
            nextQuestion()
            updateQuestion()
        }
    }

    private fun resetState(){
        binding.backButton.setOnClickListener {
        if(currentIndex > 0 && currentIndex <= questionsBank.size){
            binding.backButton.isEnabled = true
            binding.backButton.text = "Предыдущий вопрос"
            nextQuestion()
            updateQuestion()
        }else if(currentIndex == 0){
            nextQuestion()
            updateQuestion()
        }
    }
}
    private fun updateQuestion(){
        val questionResId = questionsBank[currentIndex].textResId
        binding.questionTextView.setText(questionResId)
    }

    private fun nextQuestion(){
        currentIndex = (currentIndex + 1) % questionsBank.size
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