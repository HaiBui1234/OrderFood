package com.example.orderfood.DatabaseApp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.orderfood.allModel.CartModel;

@Database(entities = {CartModel.class},version = 1)
public abstract class MyDataBase extends RoomDatabase {
       public static final String DATABASE="CART";

       public static MyDataBase Instance;
       public static synchronized MyDataBase getInstance(Context context){
              if (Instance==null){
                     Instance= Room.databaseBuilder(context.getApplicationContext(),MyDataBase.class,DATABASE)
                             .allowMainThreadQueries()
                             .build();
              }
              return Instance;
       }
       public abstract MyDAO myDAO();
}
