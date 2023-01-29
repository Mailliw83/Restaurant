package fr.isen.maignent.androiderestaurant

import android.content.Intent
import android.graphics.Insets.add
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import android.view.Window
import android.widget.Adapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.maignent.androiderestaurant.model.Data
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Text
import java.util.ArrayList

class MenuActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        title = intent.getStringExtra("category").toString()
        recyclerView= findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(null)

        loadDishesFromAPI()
    }
        private fun loadDishesFromAPI(){
            val jsonObject = JSONObject()
            jsonObject.put("id_shop", "1")
            //Adapter
            val request = JsonObjectRequest(Request.Method.POST,
                "http://test.api.catering.bluecodegames.com/menu",
                jsonObject,
                { response ->
                    try {
                        val datas = Gson().fromJson(response.toString(), Data::class.java)
                        recyclerView.adapter =
                            datas.data.first() {
                                it.name_fr == intent.getStringExtra("category").toString()
                            }?.items.let {
                                DishesAdapter(it) {
                                    val intent = Intent(this, DetailsDishesActivity::class.java)
                                    //Toast.makeText(this, "You selected ${it.name_fr}", Toast.LENGTH_SHORT).show()
                                    intent.putExtra("item", it)
                                    startActivity(intent)
                                } }
                    } catch (e: JSONException) {
                        Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_LONG).show()
                    }
                },
                { error ->
                    Toast.makeText(this, "Error connecting to API: $error", Toast.LENGTH_LONG).show()
                })
            Volley.newRequestQueue(this).add(request)
        }


}

