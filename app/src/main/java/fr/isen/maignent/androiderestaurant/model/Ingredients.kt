package fr.isen.maignent.androiderestaurant.model

import java.io.Serializable
import java.util.*

data class Ingredients(
    var id: Int,
    var id_shop: Int,
    var name_fr: String,
    var name_en: String,
    var create_date: String,
    var update_date: String,
    var id_pizza: Int
) : Serializable
