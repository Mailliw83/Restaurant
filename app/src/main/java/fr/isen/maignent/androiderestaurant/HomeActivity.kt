package fr.isen.maignent.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.Toast



class HomeActivity : AppCompatActivity() {
    private lateinit var entree:Button
    private lateinit var plats:Button
    private lateinit var desserts:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        entree = findViewById(R.id.entrees)
        addLink(entree)
        plats = findViewById(R.id.plats)
        addLink(plats)
        desserts = findViewById(R.id.desserts)
        addLink(desserts)
    }
    fun addLink(button: Button){
        button.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("category", button.text)
            startActivity(intent)
            Toast.makeText(this, "You selected ${button.text}", Toast.LENGTH_LONG).show()
        }
    }

}


