package fr.isen.maignent.androiderestaurant.model

import java.io.Serializable

data class Category(
    var name_fr:String,
    var name_en:String,
    var items: Array<Plats>
) : Serializable {
}
