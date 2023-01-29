package fr.isen.maignent.androiderestaurant.model

import java.io.Serializable
import java.util.*

data class Prix(
    var id: Int,
    var id_pizza: Int,
    var id_size:Int,
    var price:Float,
    var create_date: String,
    var update_date: String,
    var size:String
) : Serializable
