package ru.korolenkoe.calculator

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val one: Button = findViewById(R.id.one);
        val two: Button = findViewById(R.id.two);
        val three: Button = findViewById(R.id.three);
        val four: Button = findViewById(R.id.four);
        val five: Button = findViewById(R.id.five);
        val six: Button = findViewById(R.id.six);
        val seven: Button = findViewById(R.id.seven);
        val eight: Button = findViewById(R.id.eight);
        val nine: Button = findViewById(R.id.nine);
        val zero: Button = findViewById(R.id.zero);
        val plus: Button = findViewById(R.id.plus);
        val minus: Button = findViewById(R.id.minus);
        val division: Button = findViewById(R.id.devorce);
        val mul: Button = findViewById(R.id.mul);
        val equal: Button = findViewById(R.id.numEq)
        val deleteAll: Button = findViewById(R.id.allclear)
        val deleteLast: Button = findViewById(R.id.del)

        val textView: TextView = findViewById(R.id.textView)
        textView.setTextColor(Color.WHITE)
        textView.text = ""
        textView.textSize = 20.0F

        one.setOnClickListener {
            textView.append("1")
        }
        two.setOnClickListener {
            textView.append("2")
        }
        three.setOnClickListener {
            textView.append("3")
        }
        four.setOnClickListener {
            textView.append("4")
        }
        five.setOnClickListener {
            textView.append("5")
        }
        six.setOnClickListener {
            textView.append("6")
        }
        seven.setOnClickListener {
            textView.append("7")
        }
        eight.setOnClickListener {
            textView.append("8")
        }
        nine.setOnClickListener {
            textView.append("9")
        }
        zero.setOnClickListener {
            textView.append("0")
        }
        plus.setOnClickListener {
            textView.append(" + ")
        }

        minus.setOnClickListener {
            textView.append(" - ")
        }
        division.setOnClickListener {
            textView.append(" ÷ ")
        }
        mul.setOnClickListener {
            textView.append(" * ")
        }
        deleteAll.setOnClickListener {
            textView.text = ""
        }

        deleteLast.setOnClickListener {
           val text = textView.text.toString().dropLast(1)
            textView.text = text
        }

        equal.setOnClickListener {
            val t = textView.text.toString()
            if(t != "" && t.split(" ").toTypedArray().size==3){
            val text: String = textView.text.toString()
            val strs = text.split(" ").toTypedArray()

            var res = 0.0

            when(strs[1]){
                "+" -> res = strs[0].toDouble() + strs[2].toDouble()
                "-" -> res = strs[0].toDouble() - strs[2].toDouble()
                "*" -> res = strs[0].toDouble() * strs[2].toDouble()
                "÷" -> res = strs[0].toDouble() / strs[2].toDouble()
            }

            textView.text = res.toString()
        } else{
                textView.text = ""
            }
        }
    }
}