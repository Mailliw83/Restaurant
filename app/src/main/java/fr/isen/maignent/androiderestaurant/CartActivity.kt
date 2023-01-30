package fr.isen.maignent.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.maignent.androiderestaurant.databinding.ActivityCartBinding
import fr.isen.maignent.androiderestaurant.model.Items
import java.io.File

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        supportActionBar?.hide()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title.text = "Cart"
        binding.checkoutButton.text = "Checkout"

        binding.checkoutButton.setOnClickListener {
            val file = File(this.filesDir, "cart.json")
            if (file.exists()){
                val json = file.readText()
                val cart = Gson().fromJson(json, Array<Items>::class.java)
                if (cart.isNotEmpty()){
                    file.delete()
                    refreshCart()
                    updateLayout()
                    Snackbar.make(binding.root, "Commande passée", Snackbar.LENGTH_SHORT).show()
                }
            }
            refreshCart()
            updateLayout()
        }

        refreshCart()
        updateLayout()
    }

    private fun updateLayout(){
        val file = File(filesDir, "cart.json")
        if (file.exists()){
            val json = file.readText()
            val cart = Gson().fromJson(json, Array<Items>::class.java)
            binding.cartRecyclerView.layoutManager = LinearLayoutManager(null)
            binding.cartRecyclerView.adapter = CartAdapter(cart){ target ->
                File(this.filesDir, "cart.json").writeText(Gson().toJson(cart.filter { it != target }.toTypedArray()))
                refreshCart()
                updateLayout()
            }
        }
        else{
            binding.cartRecyclerView.adapter = null
        }
    }
    private fun refreshCart(){
        val file = File(filesDir, "cart.json")
        if (file.exists()){
            val json = file.readText()
            val cart = Gson().fromJson(json, Array<Items>::class.java)
            if (cart.isNotEmpty()){
                binding.toolbar.pastille.visibility = ImageView.VISIBLE
                }
            else{
                binding.toolbar.pastille.visibility = ImageView.GONE
            }
            binding.toolbar.pastille.text = cart.size.toString()
        }
        else
            binding.toolbar.pastille.visibility = ImageView.GONE
    }
    override fun onResume() {
        super.onResume()
        refreshCart()
    }



}