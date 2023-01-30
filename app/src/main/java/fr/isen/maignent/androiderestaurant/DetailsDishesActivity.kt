package fr.isen.maignent.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView

import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

import fr.isen.maignent.androiderestaurant.databinding.ActivityDetailsDishesBinding
import fr.isen.maignent.androiderestaurant.model.Items
import fr.isen.maignent.androiderestaurant.model.Plats
import java.io.File


class DetailsDishesActivity : AppCompatActivity() {

    private lateinit var plat: Plats
    private lateinit var binding: ActivityDetailsDishesBinding
    private var quantity: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_dishes)

        supportActionBar?.hide()

        binding = ActivityDetailsDishesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        plat = intent.getSerializableExtra("item") as Plats

        binding.toolbar.title.text = "Détails du plat"
        binding.toolbar.cart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        refreshCart()

        binding.imagePlat.adapter = ImageAdapter(this, plat.images)
        binding.namePlat.text = plat.name_fr
        binding.ingredientsPlat.text = plat.ingredients.joinToString(", ") { it.name_fr }

        plat.prices.forEach { price ->
            val button = (Button(this))
            button.id = price.id
            button.text = "${price.size} : ${(price.price * quantity).toString().replace(".", "€")}0"
            button.setOnClickListener {
                addInJson(price.price)
                refreshCart()
                Snackbar.make(binding.root, "Plat ajouté au panier", Snackbar.LENGTH_SHORT).show()
            }
            binding.buttonAddToCart.addView(button)
        }
        binding.quantityMinus.setOnClickListener {
            if (binding.quantityPlat.text.toString().toInt() > 1) {
                quantity--
                changePrice()
                binding.quantityPlat.text = quantity.toString()
                }
            }
        binding.quantityPlus.setOnClickListener {
            quantity++
            changePrice()
            binding.quantityPlat.text = quantity.toString()
        }
    }
    private fun refreshCart() {
        val file = File(filesDir, "cart.json")
        if (file.exists()) {
            val json = file.readText()
            val cart = Gson().fromJson(json, Array<Items>::class.java)
            if (cart.isNotEmpty()) {
                binding.toolbar.pastille.visibility = ImageView.VISIBLE
            } else {
                binding.toolbar.pastille.visibility = ImageView.GONE
            }
            binding.toolbar.pastille.text = cart.size.toString()
        }
        else{
            binding.toolbar.pastille.visibility = ImageView.GONE
        }
    }

    private fun addInJson(price: Float){
        val file = File(this.filesDir, "cart.json")
        if (!file.exists()){
            file.createNewFile()
            file.writeText("[{ \"id\": ${plat.id}, \"name\": \"${plat.name_fr}\", \"price\": $price, \"quantity\": $quantity, \"image\": \"${plat.images[0]}\"}]")
        }
        else{
            val json = file.readText()
            if (json == "[]"){
                file.writeText("[{ \"id\": ${plat.id}, \"name\": \"${plat.name_fr}\", \"price\": $price, \"quantity\": $quantity, \"image\": \"${plat.images[0]}\"}]")
            }
            else{
               file.writeText(json.substring(0, json.length - 1) + ", { \"id\": ${plat.id}, \"name\": \"${plat.name_fr}\", \"price\": $price, \"quantity\": $quantity, \"image\": \"${plat.images[0]}\"}]")
            }

        }
    }
    private fun changePrice(){
        plat.prices.forEach {
            findViewById<Button>(it.id).text = "${it.size} : ${(it.price * quantity).toString().replace(".", "€")}0"
        }
    }
    override fun onRestart(){
        super.onRestart()
        refreshCart()
    }
}
