package com.example.carbon_footprint_app

import com.google.gson.annotations.SerializedName

data class AirPollutionData(
    @SerializedName("list")
    val list: List<AirPollutionResponse>
)

data class AirPollutionResponse(
    @SerializedName("main")
    val main: AirQualityIndex,
    @SerializedName("components")
    val components: PollutantComponents,
)

data class AirQualityIndex(
    @SerializedName("aqi")
    val airQualityIndex: Int
)

data class PollutantComponents(
    @SerializedName("co")
    val carbonMonoxide: Double,

    @SerializedName("no2")
    val nitrogenDioxide: Double,

    @SerializedName("o3")
    val ozone: Double,

    @SerializedName("so2")
    val sulfurDioxide: Double,

    @SerializedName("pm2_5")
    val particulateMatter2_5: Double,

    @SerializedName("pm10")
    val particulateMatter10: Double
)