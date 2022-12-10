package com.example.playlistmaker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.ViewUtils.hideKeyboard

class SearchActivity : AppCompatActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        val backButton = findViewById<ImageView>(R.id.back_button)

        if (savedInstanceState != null){
            val text = savedInstanceState.getString(PRODUCT_AMOUNT)
            if (!text.isNullOrEmpty()){
                inputEditText.setText(text)
            }
        }
        clearButton.setOnClickListener{
            inputEditText.setText("")
            hideKeyboard(currentFocus ?: View(this))
        }
        backButton.setOnClickListener{
            finish()
        }
        val simpleTextWatcher = object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButton.visibility = clearButtonVisibility(p0)

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }

        inputEditText.addTextChangedListener(simpleTextWatcher)

    }
    private fun  clearButtonVisibility(p0: CharSequence?): Int{
        return  if (p0.isNullOrEmpty()){
            View.GONE
        }else{
           View.VISIBLE
        }

    }
    companion object {
        const val PRODUCT_AMOUNT = "PRODUCT_AMOUNT"
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val inputEditText  = findViewById<EditText>(R.id.inputEditText)
        outState.putString(PRODUCT_AMOUNT,  inputEditText.toString())
    }





}