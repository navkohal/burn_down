package com.navdeep.burn_down.db

import android.content.Context

class DatabaseService(context: Context) : QueriesInterface {

    private val dao: RepoDao = AppDatabase.getDatabase(context).deviceDao()

//    override fun getAllProfiles(): List<ProfileDataClass>? {
//        return dao.allProfile
//    }

//    override fun insertProfileData(vararg threads: ProfileDataClass): Int {
//        return try {
//            dao.insert(*threads)
//            1
//        } catch (e: Exception) {
//            e.printStackTrace()
//            0
//        }
//    }


//    override fun deleteProfile(phone: String): Int {
//        return try {
//            dao.deleteProfile(phone)
//            1
//        } catch (e: Exception) {
//            e.printStackTrace()
//            0
//        }
//    }

    override fun insertFavoriteWorkout(vararg threads: FavoriteDataClass): Int {
        return try {
            dao.insert(*threads)
            1
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    override fun getAllFavorites(): List<FavoriteDataClass> {
        return dao.getAllFavorites()
    }

//    override fun checkIfFavorite(keyId: String): Boolean {
//        return dao.checkIfFavoriteAlready(keyId)
//    }
//
//    override fun deleteFavorite(keyId: String): Int {
//        return try {
//            dao.deleteFavorite(keyId)
//            1
//        } catch (e: Exception) {
//            e.printStackTrace()
//            0
//        }
//    }
//
//    override fun getWholeCart(): List<CartDataClass>? {
//        return dao.allCartProducts
//    }
//
//    override fun addToCart(vararg threads: CartDataClass): Int {
//        return try {
//            dao.insert(*threads)
//            1
//        } catch (e: Exception) {
//            e.printStackTrace()
//            0
//        }
//    }
//
//    override fun updateCart(quantity: String?, nid: String) {
//        return dao.updateCart(quantity,nid)
//    }
//
//    override fun deleteCartItem(keyId: String): Int {
//        return try {
//            dao.deleteCartItem(keyId)
//            1
//        } catch (e: Exception) {
//            e.printStackTrace()
//            0
//        }
//    }
//
//    override fun checkIfAvailableInCart(nid: String): Boolean {
//        return dao.checkIfItemAlreadyAdded(nid)
//    }



//    override fun updateCart(quantity: String?, id: Int?) {
//        return dao.update(quantity,id)
//    }

//    override fun updateCart(cartDataClass: CartDataClass) {
//        return dao.update(cartDataClass)
//    }

//    override fun deleteCart(cartDataClass: CartDataClass) {
//        return dao.deleteCart(cartDataClass)
//    }
//




//
//    override fun checkIfImageAvailable(imageId: Int): Boolean {
//        return dao.checkIfImageIsFavourite(imageId)
//    }
//override fun getProfileById(imageId: Int): List<Image>? {
//    return dao.getImageByImageId(imageId)
//}
//    override fun deleteFavouriteImage(imageId: Int): Int {
//        return try {
//            dao.deleteImage(imageId)
//            1
//        } catch (e: Exception) {
//            e.printStackTrace()
//            0
//        }
//    }


}