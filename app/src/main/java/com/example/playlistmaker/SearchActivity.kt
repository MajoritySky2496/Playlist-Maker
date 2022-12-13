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
    val inputEditText  = findViewById<EditText>(R.id.inputEditText)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        val backButton = findViewById<ImageView>(R.id.back_button)

        clearButton.setOnClickListener{
            inputEditText.setText("")
            hideKeyboard(currentFocus ?: View(this))
        }
        backButton.setOnClickListener{
            finish()
        }
        val simpleTextWatcher = object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {
                clearButton.visibility = clearButtonVisibility(s)

            }

            override fun afterTextChanged(s: Editable?) {

            }

        }

        inputEditText.addTextChangedListener(simpleTextWatcher)

    }


    private fun  clearButtonVisibility(s: CharSequence?): Int{
        return  if (s.isNullOrEmpty()){
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
        outState.putString(PRODUCT_AMOUNT,  inputEditText.toString())
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        var text = inputEditText.toString()
        text = savedInstanceState.getString(PRODUCT_AMOUNT).toString()

        }


}