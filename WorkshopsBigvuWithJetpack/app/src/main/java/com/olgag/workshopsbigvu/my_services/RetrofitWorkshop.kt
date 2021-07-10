package com.olgag.workshopsbigvu.my_services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitWorkshop {

    private var retrofit: Retrofit? = null

    fun getWorkshops(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}