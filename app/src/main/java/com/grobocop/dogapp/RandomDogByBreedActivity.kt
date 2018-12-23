package com.grobocop.dogapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.view.*
import org.json.JSONObject

class RandomDogByBreedActivity : AppCompatActivity() {

    var imageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_dog_by_breed)

        findViewById<TextView>(R.id.raceTextView).apply {
            text = intent.getStringExtra(EXTRA_DOG_BREED)
        }

        imageUrl = intent.getStringExtra(EXTRA_URL)

        getRandomImage()

        val button = findViewById<Button>(R.id.get_dog_button)
        button.setOnClickListener{
            getRandomImage()
        }


    }

    fun getRandomImage() {
        val cache = DiskBasedCache(cacheDir, 1024 * 1024)
        val network = BasicNetwork(HurlStack())
        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, imageUrl, null,
            Response.Listener { response ->
                loadImage(response)
            },
            Response.ErrorListener { }
        )
        requestQueue.add(jsonObjectRequest)

    }

    fun loadImage(response : JSONObject){
        val dogImageView = findViewById<ImageView>(R.id.dogImageView)
        val url = response.getString("message")
        Glide
            .with(this)
            .load(url)
            .into(dogImageView)
    }
}
