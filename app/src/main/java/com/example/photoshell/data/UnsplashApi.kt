package com.example.photoshell.data

import com.example.photoshell.data.retrofitclasses.Collection
import com.example.photoshell.data.retrofitclasses.MyProfile
import com.example.photoshell.data.retrofitclasses.SearchPhotoResult
import com.example.photoshell.data.retrofitclasses.UnsplashPhoto
import com.example.photoshell.data.retrofitclasses.UnsplashUser
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {
    @GET("/photos")
    suspend fun getPhotos(
        @Header("Authorization") accessToken: String,
        @Query("page") page: Int
    ): List<UnsplashPhoto>

    @GET("/photos/{id}")
    suspend fun getPhoto(
        @Header("Authorization") accessToken: String,
        @Path("id") id: String
    ): UnsplashPhoto

    @POST("/photos/{id}/like")
    suspend fun likePhoto(
        @Header("Authorization") accessToken: String,
        @Path("id") id: String
    )

    @DELETE("/photos/{id}/like")
    suspend fun unlikePhoto(
        @Header("Authorization") accessToken: String,
        @Path("id") id: String
    )

    @GET("/photos/{id}/download")
    suspend fun trackPhotoToDownload(
        @Header("Authorization") accessToken: String,
        @Path("id") id: String
    )

    @GET("/search/photos")
    suspend fun searchPhotos(
        @Header("Authorization") accessToken: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): SearchPhotoResult

    @GET("/collections")
    suspend fun getCollections(
        @Header("Authorization") accessToken: String,
        @Query("page") page: Int
    ): List<Collection>

    @GET("/collections/{id}")
    suspend fun getCollection(
        @Header("Authorization") accessToken: String,
        @Path("id") id: String
    ): Collection

    @GET("/collections/{id}/photos")
    suspend fun getPhotosInCollection(
        @Header("Authorization") accessToken: String,
        @Path("id") id: String,
        @Query("page") page: Int
    ): List<UnsplashPhoto>

    @GET("/me")
    suspend fun getMyProfile(@Header("Authorization") accessToken: String): MyProfile

    @GET("/users/{username}")
    suspend fun getUser(
        @Header("Authorization") accessToken: String,
        @Path("username") username: String,
    ): UnsplashUser

    @GET("/users/{username}/likes")
    suspend fun getUserLikedPhotos(
        @Header("Authorization") accessToken: String,
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<UnsplashPhoto>
}