package fr.isen.maignent.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DetailsDishesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_dishes)
        title = intent.getStringExtra("item").toString()


    }
}