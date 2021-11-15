package com.example.localeApp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var mHelloTextView: TextView? = null
    private var mNameEditText: EditText? = null
    private var button: ImageButton? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mHelloTextView = findViewById(R.id.textView)
        mNameEditText = findViewById(R.id.editTextTextPersonName)
        button = findViewById(R.id.imageButton)

        button?.setOnClickListener{
            onClick()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onClick() {
        if (mNameEditText == null) {
            mHelloTextView!!.text = "Hello Kitty!"
        } else {
            mHelloTextView!!.text = "Hi," + mNameEditText!!.text;
        }
    }
}