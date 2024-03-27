package com.navdeep.burn_down.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface RepoDao {
    @Insert
    fun insert(vararg profileDataClasses: ProfileDataClass)

    @Query("SELECT * FROM profile")
    fun getProfileData(): ProfileDataClass

    @Insert
    fun insert(vararg date: AppInstalledDate)

    @Query("SELECT * FROM app_installed_date")
    fun getAppInstalledDate(): AppInstalledDate


//        @Query("Delete FROM Profile where business_phone = :business_phone")
//        int deleteProfile(String business_phone);

    @Insert
    fun insert(vararg favorites: FavoriteDataClass)

    @Query("SELECT * FROM add_favorite")
    fun getAllFavorites(): List<FavoriteDataClass>

    //    @Query("Select * FROM Favorite where key_id = :key_id")
//    boolean checkIfFavoriteAlready(String key_id);
//
//    @Query("Delete FROM Favorite where key_id = :key_id")
//    int deleteFavorite(String key_id);
//
//    @Insert
//    void insert(CartDataClass... cartDataClasses);
//
//    @Query("SELECT * FROM Cart")
//    List<CartDataClass> getAllCartProducts();
//
//    @Query("UPDATE Cart SET quantity =:quantity WHERE nid =:nid")
//    void updateCart(String quantity, String nid);
//
//    @Query("Select * FROM Cart where nid = :nid")
//    boolean checkIfItemAlreadyAdded(String nid);
//
//    @Query("Delete FROM Cart where nid = :nid")
//    int deleteCartItem(String nid);
//
//    @Delete
//    void deleteCart(CartDataClass... cartDataClasses);
}