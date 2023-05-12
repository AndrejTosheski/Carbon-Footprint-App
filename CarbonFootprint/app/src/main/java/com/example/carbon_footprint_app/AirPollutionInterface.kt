package com.example.carbon_footprint_app

import retrofit2.http.GET
import retrofit2.http.Url
import retrofit2.Call

interface interfaceGet {
    @GET
    fun getAirPollutionData(@Url url: String): Call<AirPollutionData>
}