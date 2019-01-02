package com.grobocop.dogapp


import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.*
import kotlinx.android.synthetic.main.list_item.view.*
import com.bumptech.glide.Glide
import org.json.JSONObject

const val EXTRA_DOG_BREED = "com.grobocop.dogapp.breed"
const val EXTRA_URL = "com.grobocop.dogapp.url"


class DogAdapter(val items: ArrayList<Dog>, private val context: Context) : RecyclerView.Adapter<ViewHolder1>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder1 {

        return ViewHolder1(LayoutInflater.from(p0.context).inflate(R.layout.list_item, p0, false))
    }

    override fun onBindViewHolder(p0: ViewHolder1, position: Int) {
        val dog = items.get(position)
        p0.tvDog.text = dog.dogName
        p0.tvInfo.text = dog.information
        getImage(p0, dog.photoURL)
        p0.button.setOnClickListener {
            randomDogButtonClicked(dog)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getImage(p0: ViewHolder1, imageUrl: String) {
        val queue = Volley.newRequestQueue(p0.context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, imageUrl, null,
            Response.Listener { response ->
                loadImage(p0, response)
            },
            Response.ErrorListener { }
        )
        queue.add(jsonObjectRequest)
    }

    fun loadImage(p0: ViewHolder1, response: JSONObject) {

        Glide
            .with(p0.imView.context)
            .load(response.getString("message"))
            .into(p0.imView)


    }

    fun randomDogButtonClicked(dog: Dog) {
        val intent = Intent(this.context, RandomDogByBreedActivity::class.java).apply {
            if (dog.information != "") {
                putExtra(EXTRA_DOG_BREED, dog.dogName + " " + dog.information)
            } else {
                putExtra(EXTRA_DOG_BREED, dog.dogName)
            }
            putExtra(EXTRA_URL, dog.photoURL)
        }
        this.context.startActivity(intent)
    }

}

class ViewHolder1(view: View) : RecyclerView.ViewHolder(view) {
    val tvDog = view.nameView
    val tvInfo = view.infoView
    val imView = view.list_image_view
    val button = view.random_dog_button
    val context = view.context
}