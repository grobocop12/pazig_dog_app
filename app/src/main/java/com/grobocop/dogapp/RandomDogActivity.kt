package com.grobocop.dogapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.Request
import com.bumptech.glide.Glide


class RandomDogActivity : AppCompatActivity() {

    private val dogApiUrl = "https://dog.ceo/api/breeds/image/random"
   // val button = findViewById<Button>(R.id.random_dog_button)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_dog)

/*
        button.setOnClickListener{
            randomDogButtonClicked()
        }
*/
        loadRandomDogImage()

    }


    fun randomDogButtonClicked() {
        loadRandomDogImage()
    }

    fun loadRandomDogImage() {
        val dogImageView = findViewById<ImageView>(R.id.dogImageView)
        var url = ""
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, dogApiUrl, null,
            Response.Listener{response ->
                url = response.getString("message")
            },
            Response.ErrorListener {  }
            )

        Glide
            .with(this)
            .load("https://images.dog.ceo/breeds/greyhound-italian/n02091032_904.jpg")
            .into(dogImageView)


    }
}
