package com.anjelitahp0044.assessment3_mobpro.network

import com.anjelitahp0044.assessment3_mobpro.model.Barang
import com.anjelitahp0044.assessment3_mobpro.model.OpStatus
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

private const val BASE_URL = "https://3cae-182-253-194-62.ngrok-free.app/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BarangApiService {
    @GET("api/barang")
    suspend fun getBarang(
        @Header("Authorization") userId: String
    ): List<Barang>

    @Multipart
    @POST("api/barang")
    suspend fun postBarang(
        @Header("Authorization") userId: String,
        @Part("nama") nama: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @Multipart
    @POST("api/barang/{id}")
    suspend fun updateBarang(
        @Path("id") id : String,
        @Header("Authorization") userId: String,
        @Part("nama") nama: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @FormUrlEncoded
    @POST("api/barang/{id}/update-info")
    suspend fun updateBarangWithoutImage(
        @Path("id") id: String,
        @Field("userId") userId: String,
        @Field("nama") nama: String,
        @Field("deskripsi") deskripsi: String
    ): OpStatus

    @DELETE("api/barang/{id}")
    suspend fun deleteData(
        @Header("Authorization") userId: String,
        @Path("id") id : String
    ): OpStatus
}

object BarangApi {
    val service: BarangApiService by lazy {
        retrofit.create(BarangApiService::class.java)
    }

    fun getBarangUrl(imageId: String): String {
        return "${BASE_URL}storage/$imageId"
    }
}

enum class ApiStatus { LOADING, SUCCESS,FAILED}