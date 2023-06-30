package com.example.orderfood.DatabaseApp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.orderfood.allModel.CartModel;

import java.util.List;

@Dao
public interface MyDAO {
   @Insert
   void AddCart(CartModel cartModel);
   @Delete
   void DeleteCart(CartModel cartModel);
   @Update
   void UpdateCart(CartModel cartModel);
   @Query("SELECT * FROM CartModel")
   List<CartModel> getAllCart();
   @Query("SELECT * FROM CartModel WHERE id = :ID")
   CartModel findByID(String ID);
   @Query("SELECT SUM(priceFood*quantityFood) FROM CartModel WHERE priceFood>0")
   float sumMoney();
   @Query("DELETE FROM CartModel")
   void DeleteAll();
}
