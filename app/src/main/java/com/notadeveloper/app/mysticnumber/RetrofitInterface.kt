package com.notadeveloper.app.mysticnumber

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by krsnv on 06-Aug-17.
 * //Retrofit Interface for Public Api http://numbersapi.com/#42
 */
interface RetrofitInterface {
  @GET("{number}")
  fun searchByTrivia(@Path("number") number: String): Call<String>

  @GET("{number}/math")
  fun searchByMath(@Path("number") year: String): Call<String>

  @GET("{month}/{day}/date")
  fun searchByDate(@Path("month") month: String, @Path("day") day: String): Call<String>

  @GET("{year}/year")
  fun searchByYear(@Path("year") year: String): Call<String>

  @GET("random/trivia")
  fun getRandomTrivia(): Call<String>

  @GET("random/math")
  fun getRandomMath(): Call<String>

  @GET("random/date")
  fun getRandomDate(): Call<String>

  @GET("random/year")
  fun getRandomyear(): Call<String>


  companion object Factory {
    fun create(): RetrofitInterface {

      val retrofit = Retrofit.Builder()
          .client(OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build())
          .baseUrl("http://numbersapi.com/")
          .addConverterFactory(ScalarsConverterFactory.create())
          .addConverterFactory(MoshiConverterFactory.create())
          .build()

      return retrofit.create(RetrofitInterface::class.java)
//        USAGE
//        val apiService = RetrofitInterface.create()
//        apiService.search(/* search params go in here */)
    }
  }

}