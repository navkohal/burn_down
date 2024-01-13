package com.navdeep.burn_down.dashboard.model

import com.google.gson.annotations.SerializedName
import com.navdeep.burn_down.model.BaseResponse


data class BaseWorkoutModelClass (

  @SerializedName("response" ) var response : ArrayList<BaseResponse>

)
