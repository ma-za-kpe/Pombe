package com.maku.pombe.common.data.cache.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.maku.pombe.common.data.cache.model.cachedCategory.CategoryDbModel
import io.reactivex.Flowable

/**
 * Dao for managing Category table in the database.
 */
@Dao
interface CategoryDao {

  @Query("SELECT * FROM CategoryDbModel")
  fun getAll(): Flowable<List<CategoryDbModel>>

  @Query("SELECT * FROM CategoryDbModel")
  fun getAllSync(): List<CategoryDbModel>


  @Query("SELECT * FROM CategoryDbModel WHERE id LIKE :id")
  fun findById(id: Long): LiveData<CategoryDbModel>

  @Query("SELECT * FROM CategoryDbModel WHERE id LIKE :id")
  fun findByIdSync(id: Long): CategoryDbModel

  @Insert
  fun insertAll(vararg categoryDbModels: CategoryDbModel)
}
