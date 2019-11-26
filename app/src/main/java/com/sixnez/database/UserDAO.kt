package com.sixnez.database

import androidx.room.*
import com.sixnez.model.User

@Dao
interface UserDAO
{
    @Insert
    fun insert(user: User): Long

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * from user WHERE id = :key")
    fun get(key: Long): User?

    @Query("SELECT * FROM user ORDER BY id DESC LIMIT 1")
    fun getLastUser(): User?

    @Query("SELECT * FROM user")
    fun getUsers(): List<User>?

    @Query("SELECT id FROM user WHERE login = :login AND password = :password LIMIT 1")
    fun testLogin(login: String, password: String): Long

    @Query("SELECT id FROM user WHERE login = :login LIMIT 1")
    fun existsLogin(login: String): Long
}