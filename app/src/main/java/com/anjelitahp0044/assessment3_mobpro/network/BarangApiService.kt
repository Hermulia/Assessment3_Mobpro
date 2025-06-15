package com.anjelitahp0044.assessment3_mobpro.network

import com.anjelitahp0044.assessment3_mobpro.model.Barang
import com.anjelitahp0044.assessment3_mobpro.model.OpStatus
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


private const val BASE_URL = "https://api.backendless.com/4DD8B9BF-8185-4D72-A56B-C6B29E20E4DB/B6290D09-2EF6-42BC-B332-9E90F056608C/data/Barang"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BarangApiService {
    @GET("barang")
    suspend fun getBarang(
        @Header("Authorization") userId: String
    ): List<Barang>

    @Multipart
    @POST("barang/store")
    suspend fun postBarang(
        @Header("Authorization") userId: String,
        @Part("namaBarang") namaBarang: RequestBody,
        @Part gambar: MultipartBody.Part
    ): OpStatus

    @Multipart
    @POST("barang/edit/{id}")
    suspend fun editBarang(
        @Header("Authorization") userId: String,
        @Path("id") id: String,
        @Part("namaBarang") namaBarang: RequestBody,
        @Part gambar: MultipartBody.Part
    ): OpStatus

    @DELETE("barang/delete/{id}")
    suspend fun deleteBarang(
        @Header("Authorization") userId: String,
        @Path("id") id: String
    ): OpStatus
}

object BarangApi {
    val service: BarangApiService by lazy {
        retrofit.create(BarangApiService::class.java)
    }

    fun getBarang(url: String): String {
        return "https://api.backendless.com/4DD8B9BF-8185-4D72-A56B-C6B29E20E4DB/B6290D09-2EF6-42BC-B332-9E90F056608C/data/Barang/storage/$url"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }