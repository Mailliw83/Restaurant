package fr.isen.maignent.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import fr.isen.maignent.androiderestaurant.databinding.ActivityCartBinding
import fr.isen.maignent.androiderestaurant.model.Items
import fr.isen.maignent.androiderestaurant.model.Plats
import java.io.File

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    private fun updateLayout(){
        val file = File(filesDir, "cart.json")
        if (file.exists()){
            val json = file.readText()
            val cart = Gson().fromJson(json, Array<Items>::class.java)
            binding.cartRecyclerView.layoutManager = LinearLayoutManager(null)
            binding.cartRecyclerView.adapter = CartAdapter(cart){ target ->
                File(this.filesDir, "cart.json").writeText(Gson().toJson(cart.filter { it != target }.toTypedArray()))
                //refreshCart
                updateLayout()
            }
        }
    }
    private fun refreshCart(){
        val file = File(filesDir, "cart.json")
        if (file.exists()){
            val json = file.readText()
            val cart = Gson().fromJson(json, Array<Items>::class.java)
            if (cart.isNotEmpty()){
                //TODO
                }
            else{
                //TODO
            }

        }
    }
}