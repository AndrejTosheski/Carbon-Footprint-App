package com.example.carbon_footprint_app.ui.airpollution

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.carbon_footprint_app.AirPollutionData
import com.example.carbon_footprint_app.R
import com.example.carbon_footprint_app.databinding.FragmentAirpollutionBinding
import com.example.carbon_footprint_app.interfaceGet
import retrofit2.Call
import retrofit2.Retrofit
import java.io.BufferedReader
import java.io.InputStreamReader
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Response
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.LocationServices
import java.util.*

class AirPollutionFragment : Fragment() {

    private var _binding: FragmentAirpollutionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(AirPollutionViewModel::class.java)

        _binding = FragmentAirpollutionBinding.inflate(inflater, container, false)
        val root: View = binding.root


        dashboardViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private lateinit var tvAirQuality: TextView
    private lateinit var tvCity: TextView
    private lateinit var tvPM2Particles: TextView
    private lateinit var tvPM10Particles: TextView
    private lateinit var tvCarbonMonoxide: TextView
    private lateinit var tvSulfurDioxide: TextView
    private lateinit var tvNitrogenDioxide: TextView
    private lateinit var tvOzone: TextView
    private lateinit var btnSearch: Button
    private lateinit var etSearchQuery: AutoCompleteTextView

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var cellValues: MutableList<String>
    private lateinit var cityValues: MutableList<String>

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvAirQuality = view.findViewById(R.id.textviewAirQuality)
        tvCity = view.findViewById(R.id.textviewCity)
        tvPM2Particles = view.findViewById(R.id.textviewPM2Info)
        tvPM10Particles = view.findViewById(R.id.textviewPM10Info)
        tvCarbonMonoxide = view.findViewById(R.id.textviewCarbonMonoxideInfo)
        tvSulfurDioxide = view.findViewById(R.id.textviewSulfurDioxideInfo)
        tvNitrogenDioxide = view.findViewById(R.id.textviewNitrogenDioxideInfo)
        tvOzone = view.findViewById(R.id.textviewOzoneInfo)
        btnSearch = view.findViewById(R.id.btnSearch)
        etSearchQuery = view.findViewById(R.id.autoCompleteText)


        // Check for location permission
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission already granted, get location
            getLocation()
        }

        btnSearch.setOnClickListener(){
            if(etSearchQuery.text.toString() != "" && etSearchQuery.text.toString() != "City name"){
                requestData()
            }else{
                Toast.makeText(requireContext(), "Please enter a city!", Toast.LENGTH_SHORT).show()
            }
        }

        getExcelData()

        // Filter Suggestions on AutoCompleteTextview
        etSearchQuery.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }
        })
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                    if (addresses != null) {
                        if (addresses.isNotEmpty()) {
                            val cityName = addresses[0].locality
                            // Do something with the city name
                            etSearchQuery.setText(cityName)
                            requestData()
                        }
                    }
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted
                getLocation()
            } else {
                //nothing
            }
        }
    }

    private fun requestData(){
        // Creating an instance of Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Creating an instance of API interface
        val requestGet = retrofit.create(interfaceGet::class.java)
        val apiKey = "1ba3eabae3f45305296e086ace4102f3"

        val values = getCityData(etSearchQuery.text.toString())
        if(values.size > 0){
            val city = values[0]
            val lat = values[1]
            val lng = values[2]
            val country = values[3]
            // Making Get Request
            val call = requestGet.getAirPollutionData("https://api.openweathermap.org/data/2.5/air_pollution?lat=$lat&lon=$lng&appid=$apiKey")

            call.enqueue(object : Callback<AirPollutionData> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<AirPollutionData>, response: Response<AirPollutionData>) {
                    val data = response.body()

                    if (data != null) {
                        tvPM2Particles.text = data.list[0].components.particulateMatter2_5.toString() + "μg/m3"
                        tvPM10Particles.text = data.list[0].components.particulateMatter10.toString() + "μg/m3"
                        tvCarbonMonoxide.text = data.list[0].components.carbonMonoxide.toString() + "μg/m3"
                        tvOzone.text = data.list[0].components.ozone.toString() + "μg/m3"
                        tvSulfurDioxide.text = data.list[0].components.sulfurDioxide.toString() + "μg/m3"
                        tvNitrogenDioxide.text = data.list[0].components.nitrogenDioxide.toString() + "μg/m3"

                        tvCity.text = "$city, $country"

                        when(data.list[0].main.airQualityIndex){
                            1 -> tvAirQuality.text = "Air Quality: Good"
                            2 -> tvAirQuality.text = "Air Quality: Fair"
                            3 -> tvAirQuality.text = "Air Quality: Moderate"
                            4 -> tvAirQuality.text = "Air Quality: Poor"
                            5 -> tvAirQuality.text = "Air Quality: Very Poor"
                        }
                    }
                }

                override fun onFailure(call: Call<AirPollutionData>, t: Throwable) {
                    // Handle the error
                }
            })
        }else{
            Toast.makeText(requireContext(), "City doesn't exist!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getExcelData(){
        val columnNumber = 1
        cellValues = mutableListOf()

        BufferedReader(InputStreamReader(requireContext().assets.open("worldcities.csv"))).use { reader ->
            var line = reader.readLine()
            while (line != null) {
                val values = line.split(",")
                val cellValue = values[columnNumber].replace("\"", "")
                cellValues.add(cellValue)
                line = reader.readLine()
            }
        }

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, cellValues)
        etSearchQuery.setAdapter(adapter)
    }

    private fun getCityData(cityName: String): MutableList<String>{
        cityValues = mutableListOf()
        BufferedReader(InputStreamReader(requireContext().assets.open("worldcities.csv"))).use { reader ->
            var line = reader.readLine()
            while (line != null) {
                val values = line.split(",")
                if (values[1].replace("\"", "").lowercase() == cityName.lowercase()) {
                    // Read the value of the column you want to read
                    val city = values[1].replace("\"", "")
                    cityValues.add(city)
                    val columnValueLat = values[2].replace("\"", "")
                    cityValues.add(columnValueLat)
                    val columnValueLng = values[3].replace("\"", "")
                    cityValues.add(columnValueLng)
                    val columnValueCountry = values[4].replace("\"", "")
                    cityValues.add(columnValueCountry)
                    // Do something with the column value
                    break // Exit the loop if the row with the search value is found
                }
                line = reader.readLine()
            }
        }
        return cityValues
    }
}