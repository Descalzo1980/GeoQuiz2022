package ru.stas.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.stas.geoquiz.databinding.ActivityCheatBinding
import ru.stas.geoquiz.databinding.ActivityMainBinding

private const val EXTRA_ANSWER_IS_TRUE =
    "ru.stas.geoquiz.answer_is_true"

const val EXTRA_ANSWER_SHOWN = "ru.stas.geoquiz.answer_shown"


class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding
    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false)

        binding.showAnswerButton.setOnClickListener {
            val answerText = when{
                answerIsTrue ->R.string.true_button
                else -> R.string.false_button
            }
            binding.answerTextView.setText(answerText)
            setAnswerShownResult(true)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }
    companion object{
        fun newIntent(packageContext: Context,answerIsTrue: Boolean): Intent{
            return Intent(packageContext,CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue)
            }
        }
    }

}