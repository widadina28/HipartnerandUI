package com.ros.belajarbaseactivity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataClassProject(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val expired: String
)