package com.navdeep.burn_down.db

import androidx.room.Query

interface QueriesInterface {

    fun getAllProfile(): ProfileDataClass

    fun insertProfileData(vararg threads: ProfileDataClass): Int
//
//    fun deleteProfile(threads: String): Int
//

    fun insertFavoriteWorkout(vararg threads: FavoriteDataClass): Int

    fun getAllFavorites(): List<FavoriteDataClass>?


//    fun checkIfFavorite(keyId: String): Boolean
//
//    fun deleteFavorite(keyId: String): Int
//
//    fun getWholeCart(): List<CartDataClass>?
//
//    fun addToCart(vararg threads: CartDataClass): Int
//
//    fun deleteCartItem(keyId: String): Int
//
//    fun checkIfAvailableInCart(nid: String): Boolean
//
//    fun updateCart(quantity : String? , nid : String)

//    fun updateCart(cartDataClass: CartDataClass)

//    @Query("UPDATE Cart SET quantity =:quantity WHERE id =:id")
//    fun updateCart(quantity: String?, id: Int?) : Int

//    fun deleteCart(cartDataClass: CartDataClass)


//
//    fun checkIfImageAvailable(imageId: Int): Boolean
//
//    fun deleteFavouriteImage(imageId: Int): Int
//    fun getImageByImageId(imageId: Int): List<Image>?
}