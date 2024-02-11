package com.navdeep.burn_down.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.navdeep.burn_down.model.BaseResponse

class Converters {

    @TypeConverter
    fun listToJson(value: List<BaseResponse>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<BaseResponse>::class.java).toList()
}