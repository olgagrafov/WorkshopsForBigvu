package com.olgag.workshopsbigvu.my_services

import com.olgag.workshopsbigvu.model.Workshop

import retrofit2.Call;
import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitServices {
    @GET("{workshops_json}")
    fun getWorkshopsJSONList( @Path("workshops_json") jsonName: String): Call<MutableList<Workshop>>
}