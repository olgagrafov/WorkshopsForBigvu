package com.olgag.workshopsbigvu.common

import com.olgag.workshopsbigvu.my_services.RetrofitServices
import com.olgag.workshopsbigvu.my_services.RetrofitWorkshop


object Common {

    private lateinit var base_url: String
    fun setUrl(con: String) {
        base_url=con
    }

    val retrofitService: RetrofitServices
        get() = RetrofitWorkshop.getWorkshops(base_url).create(RetrofitServices::class.java)
}