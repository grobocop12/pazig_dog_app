package com.grobocop.dogapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_dogs_list.*
import org.json.JSONObject

class DogsListActivity : AppCompatActivity() {

    val dogListURL = "https://dog.ceo/api/breeds/list/all"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogs_list)

        var dogs = ArrayList<Dog>()
        /*
        dogs.add(Dog("Piesek", "https://images.dog.ceo/breeds/dhole/n02115913_1323.jpg", "dhole "))
        dogs.add(Dog("Azor", "https://images.dog.ceo/breeds/coonhound/n02089078_4508.jpg", "coonhound "))
        dogs.add(Dog("Andrzej", "https://images.dog.ceo/breeds/entlebucher/n02108000_3027.jpg", "entlebucher "))
        dogs.add(Dog("Janusz", "https://images.dog.ceo/breeds/dhole/n02115913_750.jpg", "dhole "))
        dogs.add(Dog("Burek", "https://images.dog.ceo/breeds/dhole/n02115913_2320.jpg", "dhole "))
*/
        requestDogBreeds(dogs)

        val adapter = DogAdapter(dogs, this.baseContext)
        rv_dogs_list.layoutManager = LinearLayoutManager(this)
        rv_dogs_list.adapter = adapter

    }

    fun requestDogBreeds(dogList: ArrayList<Dog>) {

        val cache = DiskBasedCache(cacheDir, 1024 * 1024)
        val network = BasicNetwork(HurlStack())
        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, dogListURL, null,
            Response.Listener { response ->
                loadDogList(response, dogList)
            },
            Response.ErrorListener { }
        )
        requestQueue.add(jsonObjectRequest)
    }

    fun loadDogList(response: JSONObject, dogList: ArrayList<Dog>) {
        val message = response.getJSONObject("message")
        val names = message.names()

        for (i: Int in 0..names.length() - 1) {
            val breed = names.get(i)
            val subbreeds = message.getJSONArray(breed.toString())

            if (subbreeds.length() == 0) {
                dogList.add(
                    Dog(
                        breed.toString(),
                        "https://dog.ceo/api/breed/" + breed.toString() + "/images/random",
                        ""
                    )
                )
            } else {
                for (j: Int in 0..subbreeds.length() - 1) {
                    dogList.add(
                        Dog(
                            breed.toString(),
                            "https://dog.ceo/api/breed/" + breed.toString() + "/" + subbreeds[j].toString() + "/images/random",
                            subbreeds[j].toString()
                        )
                    )
                }
            }
        }

        val adapter = DogAdapter(dogList, this.baseContext)
        rv_dogs_list.layoutManager = LinearLayoutManager(this)
        rv_dogs_list.adapter = adapter
    }

    fun getImageUrl(imageURL: String): String {
        val cache = DiskBasedCache(cacheDir, 1024 * 1024)
        val network = BasicNetwork(HurlStack())
        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }
        var image = ""
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, dogListURL, null,
            Response.Listener { response ->
                image = response.getString("message")
            },
            Response.ErrorListener { }
        )
        requestQueue.add(jsonObjectRequest)

        return image
    }
}
