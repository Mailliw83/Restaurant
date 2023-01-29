package fr.isen.maignent.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

import fr.isen.maignent.androiderestaurant.model.Items


class CartAdapter(private val cart: Array<Items>, private val onClick: (Items) -> Unit) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView? = view.findViewById(R.id.name)
        private val price: TextView? = view.findViewById(R.id.price)
        private val images: ImageView? = view.findViewById(R.id.image)
        private val delete: Button? = view.findViewById(R.id.buttonSupp)

        fun bind(item: Items, onClick: (Items) -> Unit) {
            setTitle(item)
            setPrice(item)
            setImage(item)
            delete?.setOnClickListener {
                /*if (item.quantity > 1){
                    item.quantity -= 1
                }
                else{
                    item.quantity = 0
                }*/
                onClick(item)
            }
        }
        private fun setImage(image: Items) {
            if (image.image.isNotEmpty()){
                Picasso.get().load(image.image).into(images)
            }
        }
        private fun setTitle(title: Items) {
            name?.text = title.name
        }
        private fun setPrice(item: Items) {
            this.price?.text = (item.price * item.quantity).toString().replace(".", "€") + "0"
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_cart_items, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cart.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cart[position], onClick)
    }
}
