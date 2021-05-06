package com.example.calculator.calculatorFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.calculator.databinding.FragmentCalculatorBinding
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorFragment : Fragment() {

    // assign the _binding variable initially to null and
    // also when the view is destroyed again it has to be set to null
    private var _binding: FragmentCalculatorBinding? = null

    // with the backing property of the kotlin we extract
    // the non null value of the _binding
    private val binding get() = _binding!!
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)


        // numbers
        binding.one.setOnClickListener { appendOnExpression("1", true) }
        binding.two.setOnClickListener { appendOnExpression("2", true) }
        binding.three.setOnClickListener { appendOnExpression("3", true) }
        binding.four.setOnClickListener { appendOnExpression("4", true) }
        binding.five.setOnClickListener { appendOnExpression("5", true) }
        binding.six.setOnClickListener { appendOnExpression("6", true) }
        binding.seven.setOnClickListener { appendOnExpression("7", true) }
        binding.eight.setOnClickListener { appendOnExpression("8", true) }
        binding.nine.setOnClickListener { appendOnExpression("9", true) }
        binding.zero.setOnClickListener { appendOnExpression("0", true) }
        binding.dot.setOnClickListener { appendOnExpression(".", true) }

        //operator
        binding.plus.setOnClickListener { appendOnExpression("+", false) }
        binding.minus.setOnClickListener { appendOnExpression("-", false) }
        binding.divide.setOnClickListener { appendOnExpression("/", false) }
        binding.multiply.setOnClickListener { appendOnExpression("*", false) }
        binding.bracketOpen.setOnClickListener { appendOnExpression("(", false) }
        binding.bracketClose.setOnClickListener { appendOnExpression(")", false) }

        //clear
        binding.clear.setOnClickListener{
            binding.display1.text = ""
            binding.display2.text = ""
        }

        //backspace
        binding.backSpace.setOnClickListener{
            val string : String = binding.display1.text.toString()
            if(string.isNotEmpty())
                binding.display1.text = string.substring(0,string.length-1)
            binding.display2.text = ""

        }

        //equalTo
        binding.equalTo.setOnClickListener{
            try {
                val expression = ExpressionBuilder(binding.display1.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()

                if(result == longResult.toDouble())
                    binding.display1.text = longResult.toString()
                else
                    binding.display2.text = result.toString()

            } catch (e: Exception) {
                Toast.makeText(activity,"Invalid Expression",Toast.LENGTH_LONG).show()
                Log.i("calculatorFragment","${e.message}")
            }

        }

        return binding.root
    }

    private fun appendOnExpression(string: String, canClear : Boolean){

        if(binding.display2.text.isNotEmpty())
            binding.display1.text = ""

        if(canClear){
            binding.display2.text = ""
            binding.display1.append(string)
        } else {
            binding.display1.append(binding.display2.text)
            binding.display1.append(string)
            binding.display2.text = ""
        }
    }
}