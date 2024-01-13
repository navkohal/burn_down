package com.navdeep.burn_down.model.cardio

import com.google.gson.annotations.SerializedName

data class CardioModelClass (
    @SerializedName("bodyPart") var bodyPart         : String?           = null,
    @SerializedName("equipment") var equipment        : String?           = null,
    @SerializedName("gifUrl") var gifUrl           : String?           = null,
    @SerializedName("id") var id               : String?           = null,
    @SerializedName("name") var name             : String?           = null,
    @SerializedName("target") var target           : String?           = null,
    @SerializedName("secondaryMuscles") var secondaryMuscles : ArrayList<String> = arrayListOf(),
    @SerializedName("instructions") var instructions     : ArrayList<String> = arrayListOf()
    )