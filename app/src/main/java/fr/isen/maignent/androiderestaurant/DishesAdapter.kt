package fr.isen.maignent.androiderestaurant

import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.maignent.androiderestaurant.model.Category
import fr.isen.maignent.androiderestaurant.model.Plats

class DishesAdapter(private val menuTitles: Array<Plats>, val onClick: (String) -> Unit) :
    RecyclerView.Adapter<DishesAdapter.MenuViewHolder>() {


    class MenuViewHolder(view: View) :RecyclerView.ViewHolder(view)
    {
        val viewMenu: TextView? = view.findViewById(R.id.menuTitle)
        val image:ImageView? = view.findViewById(R.id.images)
        val prices:TableLayout? = view.findViewById(R.id.prices)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_items, parent, false)
        return MenuViewHolder(view)
    }


    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {



        if(menuTitles[position].images[0].isNotEmpty()){
            Picasso.get().load(menuTitles[position].images[0]).resize(150, 150).centerCrop().into(holder.image)
        }
        holder.viewMenu?.text = menuTitles[position].name_fr
        menuTitles[position].prices.forEach { prices ->
            val row = TableRow(holder.prices?.context)
            val size = TextView(holder.prices?.context)
            size.textSize = 12f
            size.text = prices.size+" : "
            val prix = TextView(holder.prices?.context)
            prix.gravity = Gravity.END
            prix.textSize = 12f
            prix.text = prices.price.toString().replace(".", "€") + "0"
            row.addView(prix)
            holder.prices?.addView(row)
        }
        holder.itemView.setOnClickListener {
            onClick(menuTitles[position].name_fr)
        }
    }

    override fun getItemCount(): Int {
        return menuTitles.size
    }
}