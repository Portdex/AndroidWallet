package com.application.portdex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.application.portdex.data.local.cart.CartEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: CartEntity): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsync(item: CartEntity): Long

    @Query("DELETE FROM carts WHERE packageId =:packageId")
    fun delete(packageId: String?): Single<Int>

    @Query("SELECT * FROM carts")
    fun getItems(): Flowable<List<CartEntity>>

    @Query("SELECT * FROM carts")
    fun getItemsSimple(): Single<List<CartEntity>>

    @Query("DELETE FROM carts")
    fun nukeTable(): Completable
}