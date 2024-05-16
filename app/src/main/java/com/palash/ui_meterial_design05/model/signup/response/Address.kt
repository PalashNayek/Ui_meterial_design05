package com.palash.ui_meterial_design05.model.signup.response

data class Address(
    val address: String,
    val city: String,
    val coordinates: Coordinates,
    val postalCode: String,
    val state: String
)