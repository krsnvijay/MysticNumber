package com.notadeveloper.app.mysticnumber

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val apiService = RetrofitInterface.create()
    radiogrouptype.setOnCheckedChangeListener { radioGroup, i ->
      when (i) {
        input.id -> {
          editText.isEnabled = true
          radiogroup.setOnCheckedChangeListener { radioGroup1, i1 ->
            when (i1) {
              math.id, trivia.id -> editText.inputType = InputType.TYPE_CLASS_NUMBER
              date.id -> {
                editText.inputType = InputType.TYPE_CLASS_DATETIME
                editText.hint = "MMDD"
              }
              year.id -> editText.inputType = InputType.TYPE_CLASS_NUMBER
            }
          }
        }
        random.id -> editText.isEnabled = false
      }
    }
    val callback = object : Callback<String> {
      override fun onResponse(call: Call<String>?, response: Response<String>?) {

        if (response != null && response.isSuccessful) {
          Log.e("Retrofit", response.body().toString() + response.message() + response.raw())
          resulttext.text = response.body()
        } else {
          Toast.makeText(this@MainActivity, "Error Fetching Data..", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onFailure(call: Call<String>?, t: Throwable?) {
        Toast.makeText(this@MainActivity, "Error Fetching Data", Toast.LENGTH_SHORT).show()
      }
    }
    button.setOnClickListener {
      when (radiogrouptype.checkedRadioButtonId) {
        input.id -> {
          when (radiogroup.checkedRadioButtonId) {
            math.id -> apiService.searchByMath(editText.text.toString()).enqueue(callback)
            trivia.id -> apiService.searchByTrivia(editText.text.toString()).enqueue(callback)
            date.id -> {
              if (!editText.text.toString().isEmpty() && editText.text.toString().length == 4)
                apiService.searchByDate(editText.text.toString().substring(0, 2), editText.text.toString().substring(2, 4)).enqueue(callback)
            }
            year.id -> apiService.searchByYear(editText.text.toString()).enqueue(callback)
          }
        }
        random.id -> when (radiogroup.checkedRadioButtonId) {
          math.id -> apiService.getRandomMath().enqueue(callback)
          trivia.id -> apiService.getRandomTrivia().enqueue(callback)
          date.id -> apiService.getRandomDate().enqueue(callback)
          year.id -> apiService.getRandomyear().enqueue(callback)
        }
      }
    }
  }
}
