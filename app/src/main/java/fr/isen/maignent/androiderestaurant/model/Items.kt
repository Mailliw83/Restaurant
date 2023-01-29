package fr.isen.maignent.androiderestaurant.model

import java.io.Serializable

data class Items(
    var id: String,
    var name: String,
    var quantity: Int,
    var price: Float,
    var image: String
) : Serializable
