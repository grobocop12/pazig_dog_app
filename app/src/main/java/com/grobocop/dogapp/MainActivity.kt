package com.grobocop.dogapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val random_dog_button = findViewById<Button>(R.id.random_dog_button)
        random_dog_button.setOnClickListener{
            randomDogButtonClicked()
        }

        val dogs_list_button = findViewById<Button>(R.id.dogs_list_button)
        dogs_list_button.setOnClickListener{
            dogsListButtonClicked()
        }
    }

    fun randomDogButtonClicked() {
        val intent =  Intent(this, RandomDogActivity::class.java).apply { }
        startActivity(intent)
    }

    fun dogsListButtonClicked(){
        val intent =  Intent(this, DogsListActivity::class.java).apply { }
        startActivity(intent)
    }
}
