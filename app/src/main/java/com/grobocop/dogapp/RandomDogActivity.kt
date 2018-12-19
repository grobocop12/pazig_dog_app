package com.grobocop.dogapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.bumptech.glide.Glide
import org.json.JSONObject
import java.net.URL


class RandomDogActivity : AppCompatActivity() {

    private val dogApiUrl = "https://dog.ceo/api/breeds/image/random"
    // val button = findViewById<Button>(R.id.random_dog_button)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_dog)

        getRandomDogImage()

        val button = findViewById<Button>(R.id.get_dog_button)
        button.setOnClickListener{
            randomDogButtonClicked()
        }

    }


    fun randomDogButtonClicked() {
        getRandomDogImage()
    }

    fun getRandomDogImage() {

        val cache = DiskBasedCache(cacheDir, 1024 * 1024)
        val network = BasicNetwork(HurlStack())
        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, dogApiUrl, null,
            Response.Listener { response ->
                loadImage(response)
            },
            Response.ErrorListener { }
        )
        requestQueue.add(jsonObjectRequest)
    }

    fun loadImage(response:JSONObject){
        val dogImageView = findViewById<ImageView>(R.id.dogImageView)
        val url = response.getString("message")
        val race = url.split('/')[url.split('/').count()-2]

        Glide
            .with(this)
            .load(url)
            .into(dogImageView)

        val raceTextView = findViewById<TextView>(R.id.raceTextView)
        raceTextView.text = race
    }
}
