package ru.stas.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ru.stas.geoquiz.databinding.ActivityMainBinding

private const val TAG = "QuizViewModel"
private const val EXTRA_API = "Build.VERSION_CODES.S"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }

    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            blurCheatButton()
        }
        updateQuestion()

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
            updateQuestion()
        }
        binding.falseButton.setOnClickListener {
          checkAnswer(false)
            updateQuestion()
        }
        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()

        }
        binding.questionTextView.setOnClickListener {
            updateQuestion()
        }

        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity,answerIsTrue)
            cheatLauncher.launch(intent)
            intentApi()

        }
    }

    private fun updateQuestion(){
        val questionTextId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextId)
    }

    private fun intentApi(){
        val apiVersion = Build.VERSION_CODES.S
        intentApi(apiVersion)
        val intentApi = Intent(this,CheatActivity::class.java)
        startActivity(intentApi)
    }
    private fun intentApi(apiVersion: Int) {
        val data = Intent().apply {
            putExtra(EXTRA_API, apiVersion)
        }
        setResult(Activity.RESULT_OK, data)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun blurCheatButton() {
        val effect = RenderEffect.createBlurEffect(
            15F,
            15F,
            Shader.TileMode.MIRROR
        )
        binding.cheatButton.setRenderEffect(effect)
    }

    private fun checkAnswer(userAnswer : Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show()
    }
}