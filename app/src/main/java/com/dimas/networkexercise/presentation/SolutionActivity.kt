package com.dimas.networkexercise.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.dimas.networkexercise.MainActivity
import com.dimas.networkexercise.R
import com.dimas.networkexercise.base.AppModule
import com.dimas.networkexercise.databinding.ActivitySolutionBinding
import com.dimas.networkexercise.domain.model.Fact
import com.dimas.networkexercise.domain.model.Inference
import com.dimas.networkexercise.presentation.viewmodel.AppViewModel
import com.dimas.networkexercise.utils.Error
import com.dimas.networkexercise.utils.Initiate
import com.dimas.networkexercise.utils.IntentConstant
import com.dimas.networkexercise.utils.Loading
import com.dimas.networkexercise.utils.Success
import com.dimas.networkexercise.utils.observeIn

class SolutionActivity : AppCompatActivity() {

    private val solutionViewModel by viewModels<AppViewModel> { AppModule.homeViewModelFactory }

    companion object{
        fun start(context: Context, facts: Fact) {
            val bundle = Bundle().apply {
                putParcelable(IntentConstant.FACTS, facts)
            }
            val intent = Intent(context, SolutionActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)

        }
    }

    private lateinit var binding: ActivitySolutionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySolutionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeToolbar()
        observer()
        binding.btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
        solutionViewModel.inference(
            intent.getParcelableExtra<Fact>(IntentConstant.FACTS)?.facts.orEmpty()
        )
    }

    private fun observer() {
        solutionViewModel.inference.observeIn(this) {
            when(it) {
                is Success -> showInference(it.data)
                is Error -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                is Loading -> {}
                is Initiate -> {}
            }
        }
    }

    private fun showInference(data: Inference) {
        val split = data.solutions?.desc?.split(".")
        val problemD = split?.firstOrNull().orEmpty() + "."

        with(binding) {
            problem.text = problemD
            solutionDesc.text =data.solutions?.desc?.replace(problemD, "")
            solutionCode.text = String.format(getString(R.string.label_kode_solusi_s), data.solutions?.code.orEmpty())
            ruleCode.text = String.format(getString(R.string.label_kode_aturan_s), data.triggeredRule?.ruleCode.orEmpty())
            triggeredFact.text = String.format(getString(R.string.label_fakta_yang_dipicu_s), data.triggeredRule?.triggeredFact.orEmpty())
        }
    }

    private fun initializeToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(false)
        }
    }
}