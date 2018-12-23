package com.grobocop.dogapp


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import kotlinx.android.synthetic.main.list_item.view.*
import com.bumptech.glide.Glide
import org.json.JSONObject


class DogAdapter(val items: ArrayList<Dog>) : RecyclerView.Adapter<ViewHolder1>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder1 {

       return ViewHolder1(LayoutInflater.from(p0.context).inflate(R.layout.list_item, p0, false))
    }


    override fun onBindViewHolder(p0: ViewHolder1, position: Int) {
        val dog = items.get(position)
        p0.tvDog.text = dog.dogName
        p0.tvInfo.text = dog.information
        getImage(p0, dog.photoURL)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getImage(p0 : ViewHolder1, imageUrl: String){
        /*Glide
            .with(p0.imView.context)
            .load(imageUrl)
            .into(p0.imView)*/
        val queue = Volley.newRequestQueue(p0.context)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, imageUrl, null,
            Response.Listener { response ->
                loadImage(p0,response)
            },
            Response.ErrorListener { }
        )

        queue.add(jsonObjectRequest)



    }

    fun loadImage(p0 : ViewHolder1, response: JSONObject){

        Glide
            .with(p0.imView.context)
            .load(response.getString("message"))
            .into(p0.imView)
    }

}

class ViewHolder1(view: View) : RecyclerView.ViewHolder(view) {
    val tvDog = view.nameView
    val tvInfo =  view.infoView
    val imView = view.list_image_view
    val context = view.context
}