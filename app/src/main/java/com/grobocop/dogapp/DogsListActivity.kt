package com.grobocop.dogapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutManager
import kotlinx.android.synthetic.main.activity_dogs_list.*

class DogsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogs_list)

        var dogs = ArrayList<Dog>()
        dogs.add(Dog("Piesek", "https://images.dog.ceo/breeds/dhole/n02115913_1323.jpg", "dhole "))
        dogs.add(Dog("Azor", "https://images.dog.ceo/breeds/coonhound/n02089078_4508.jpg", "coonhound "))
        dogs.add(Dog("Andrzej", "https://images.dog.ceo/breeds/entlebucher/n02108000_3027.jpg", "entlebucher "))
        dogs.add(Dog("Janusz", "https://images.dog.ceo/breeds/dhole/n02115913_750.jpg", "dhole "))
        dogs.add(Dog("Burek", "https://images.dog.ceo/breeds/dhole/n02115913_2320.jpg", "dhole "))



        val adapter = DogAdapter(dogs)
        rv_dogs_list.layoutManager = LinearLayoutManager(this)

        rv_dogs_list.adapter = adapter
        
    }
}
