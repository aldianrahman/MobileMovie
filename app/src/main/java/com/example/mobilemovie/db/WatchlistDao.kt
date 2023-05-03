package com.example.mobilemovie.db

import androidx.room.*
import com.example.mobilemovie.db.entity.FavoriteEntity
import com.example.mobilemovie.db.entity.UserEntity
import com.example.mobilemovie.db.entity.WatchlistEntity
import com.example.mobilemovie.db.entity.relation.ImageEntity


@Dao
interface WatchlistDao {

    @Query("SELECT * FROM watchlist ORDER BY id DESC")
    fun getAllWatchlist():List<WatchlistEntity>?

    @Query("SELECT * FROM watchlist WHERE iduser =:iduser")
    fun getUserWatchlist(iduser: Int): List<WatchlistEntity>?

//    @Query("SELECT * FROM watchlist WHERE iduser=:iduser")
//    fun getUCW(iduser:Int): List<WatchlistEntity>?

    @Query("SELECT COUNT(*) FROM watchlist WHERE iduser=:iduser")
    suspend fun getUCW(iduser: Int): Int

    @Query("SELECT COUNT(*) FROM favorite WHERE iduser=:iduser")
    suspend fun getUCF(iduser: Int): Int




//    @Query("SELECT * FROM watchlist WHERE iduser =:iduser")
//    fun getUserWatchlist(iduser:Int): List<WatchlistEntity>?

    @Insert
    fun addWatchlist(watchlist: WatchlistEntity?)

    @Delete
    fun delWatchlist(watchlist: WatchlistEntity?)

    @Update
    fun updWatchlist(watchlist: WatchlistEntity?)

    @Query("SELECT * FROM favorite ORDER BY id DESC")
    fun getAllFavorite():List<FavoriteEntity>?

    @Query("SELECT * FROM favorite WHERE iduser =:iduser")
    fun getUserFavorite(iduser: Int): List<FavoriteEntity>?

    @Insert
    fun addFavorite(fav: FavoriteEntity?)

    @Delete
    fun delFavorite(fav: FavoriteEntity?)

    @Update
    fun updFavorite(fav: FavoriteEntity?)

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getUser():List<UserEntity>?

    @Query("SELECT * FROM users WHERE email AND password")
    fun getLogin():List<UserEntity>?

    @Query("SELECT * FROM users WHERE email =  :email AND password = :pass")
    fun getUserLogin(email: String, pass: String): UserEntity?

    @Query("SELECT * FROM users WHERE email =  :email AND id = :iduser AND name =:nama")
    fun getUserData(email: String, iduser: Int, nama: String): UserEntity?

    @Query("SELECT * FROM users WHERE email =  :email")
    fun getSignup(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id =  :iduser AND name =:nama")
    fun getName(iduser: Int, nama: String): UserEntity?

//    @Query("SELECT gambar FROM users WHERE id =:iduser")

    @Query("SELECT * FROM users WHERE id =:iduser")
    fun getStringImage(iduser: Int):UserEntity

    @Query("SELECT * FROM image WHERE iduser =:iduser")
    fun getBanner(iduser: Int):ImageEntity

//    @Query("SELECT * FROM users WHERE id =:iduser ORDER BY gambar")
//    fun getStringImage(iduser: Int):UserEntity





    @Insert
    fun addUser(fav: UserEntity?)

    @Delete
    fun delUser(fav: UserEntity?)

    @Update()
    fun updUser(user: UserEntity?)

    @Query("UPDATE users SET gambar=:gambar WHERE id = :id")
    fun updUserImage(gambar: String, id: Int)

    @Query("UPDATE users SET name =:name OR email=:email OR password =:pass WHERE id = :id")
    fun updUsers(email: String,name: String,pass: String, id: Int)

    @Query("UPDATE users SET  email=:email WHERE id = :id")
    fun updUsersEmail(email: String, id: Int)

    @Query("UPDATE users SET  name=:name WHERE id = :id")
    fun updUsersName(name: String, id: Int)

    @Query("UPDATE users SET  password=:pass WHERE id = :id")
    fun updUsersPass(pass: String, id: Int)


    @Insert
    fun addUserBanner(fav: ImageEntity?)













//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertUser(user: UserEntity?)
////
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertDirector(director: Director)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertStudent(student: Student)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertSubject(subject: Subject)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertStudentSubjectCrossRef(crossRef: StudentSubjectCrossRef)
//
//    @Transaction
//    @Query("SELECT * FROM users WHERE email = :userName")
//    suspend fun getUserAndWatchlist(userName: String): List<UserAndWatchlist>
//
//    @Transaction
//    @Query("SELECT * FROM school WHERE schoolName = :schoolName")
//    suspend fun getSchoolWithStudents(schoolName: String): List<SchoolWithStudents>
//
//    @Transaction
//    @Query("SELECT * FROM subject WHERE subjectName = :subjectName")
//    suspend fun getStudentsOfSubject(subjectName: String): List<SubjectWithStudents>
//
//    @Transaction
//    @Query("SELECT * FROM student WHERE studentName = :studentName")
//    suspend fun getSubjectsOfStudent(studentName: String): List<StudentWithSubjects>





}
