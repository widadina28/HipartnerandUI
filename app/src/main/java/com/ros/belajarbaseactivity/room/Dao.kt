package com.ros.belajarbaseactivity.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {

    @Insert
    suspend fun add (dataclassproject: DataClassProject)

    @Update
    suspend fun update (dataclassproject: DataClassProject)

    @Delete
    suspend fun delete(dataclassproject: DataClassProject)

    @Query("SELECT * FROM dataclassproject")
    suspend fun getdcproject(): List<DataClassProject>


    @Query("SELECT * FROM dataclassproject WHERE id=:projectId")
    suspend fun getprojectId(projectId : Int) : List<DataClassProject>
}