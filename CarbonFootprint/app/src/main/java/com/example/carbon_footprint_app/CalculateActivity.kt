package com.example.carbon_footprint_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.text.isDigitsOnly

class CalculateActivity : AppCompatActivity() {

    // Deklariranje na promenlivi
    private lateinit var etElectricity: EditText
    private lateinit var etNaturalGas: EditText
    private lateinit var etCoal: EditText
    private lateinit var etWoodenPellets: EditText
    private lateinit var etCar: EditText
    private lateinit var etMotorbike: EditText
    private lateinit var etBus: EditText
    private lateinit var etTram: EditText
    private lateinit var etTaxi: EditText
    private lateinit var etSubway: EditText
    private lateinit var etTrain: EditText
    private lateinit var etFlightsShorter: EditText
    private lateinit var etFlightsLonger: EditText
    private lateinit var btnCalculate: Button

    private var electricityValue = 0
    private var naturalGasValue = 0
    private var woodenPelletsValue = 0
    private var coalValue = 0
    private var carValue = 0
    private var motorbikeValue = 0
    private var busValue = 0
    private var tramValue = 0
    private var taxiValue = 0
    private var subwayValue = 0
    private var trainValue = 0
    private var flightsShorterValue = 0
    private var flightsLongerValue = 0

    private var homeTotal = 0
    private var carMotorbikeTotal = 0
    private var publicTransportTotal = 0
    private var flightsTotal = 0
    private var total = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)

        supportActionBar?.hide()

        // Definiranje na promenlivi
        etElectricity = findViewById(R.id.editTextElectricity)
        etNaturalGas = findViewById(R.id.editTextNaturalGas)
        etCoal = findViewById(R.id.editTextCoal)
        etWoodenPellets = findViewById(R.id.editTextWoodenPellets)
        etCar = findViewById(R.id.editTextCar)
        etMotorbike = findViewById(R.id.editTextMotorbike)
        etBus = findViewById(R.id.editTextBus)
        etTram = findViewById(R.id.editTextTram)
        etSubway = findViewById(R.id.editTextSubway)
        etTaxi = findViewById(R.id.editTextTaxi)
        etTrain = findViewById(R.id.editTextTrain)
        etFlightsShorter = findViewById(R.id.editTextFlightsShorter)
        etFlightsLonger = findViewById(R.id.editTextFlightLonger)
        btnCalculate = findViewById(R.id.btCalculate)


        btnCalculate.setOnClickListener(){

            // Home

            if(etElectricity.text.toString() != "" && etElectricity.text.toString().isDigitsOnly()){
                electricityValue = (etElectricity.text.toString().toInt() * 0.44).toInt()
            }

            if(etNaturalGas.text.toString() != "" && etNaturalGas.text.toString().isDigitsOnly()){
                naturalGasValue = (etNaturalGas.text.toString().toInt() * 0.054).toInt()
            }

            if(etWoodenPellets.text.toString() != "" && etWoodenPellets.text.toString().isDigitsOnly()){
                woodenPelletsValue = (etWoodenPellets.text.toString().toInt() * 0.06).toInt()
            }

            if(etCoal.text.toString() != "" && etCoal.text.toString().isDigitsOnly()){
                coalValue = (etCoal.text.toString().toInt() * 1.0).toInt()
            }

            homeTotal = electricityValue + naturalGasValue + woodenPelletsValue + coalValue

            // Car & Motorbike

            if(etCar.text.toString() != "" && etCar.text.toString().isDigitsOnly()){
                carValue = (etCar.text.toString().toInt() * 0.24 * 0.07).toInt()
            }

            if(etMotorbike.text.toString() != "" && etMotorbike.text.toString().isDigitsOnly()){
                motorbikeValue = (etMotorbike.text.toString().toInt() * 0.24 * 0.05).toInt()
            }

            carMotorbikeTotal = carValue + motorbikeValue

            // Public Transport

            if(etBus.text.toString() != "" && etBus.text.toString().isDigitsOnly()){
                busValue = (etBus.text.toString().toInt() * 0.31 * 0.6).toInt()
            }

            if(etTram.text.toString() != "" && etTram.text.toString().isDigitsOnly()){
                tramValue = (etTram.text.toString().toInt() * 0.023).toInt()
            }

            if(etTrain.text.toString() != "" && etTrain.text.toString().isDigitsOnly()){
                trainValue = (etTrain.text.toString().toInt() * 0.041).toInt()
            }

            if(etSubway.text.toString() != "" && etSubway.text.toString().isDigitsOnly()){
                subwayValue = (etSubway.text.toString().toInt() * 0.040).toInt()
            }

            if(etTaxi.text.toString() != "" && etTaxi.text.toString().isDigitsOnly()){
                taxiValue = (etTaxi.text.toString().toInt() * 0.187).toInt()
            }

            publicTransportTotal = busValue + tramValue + trainValue + subwayValue + taxiValue

            // Flights

            if(etFlightsShorter.text.toString() != "" && etFlightsShorter.text.toString().isDigitsOnly()){
                flightsShorterValue = (etFlightsShorter.text.toString().toInt() * 0.20).toInt()
            }

            if(etFlightsLonger.text.toString() != "" && etFlightsLonger.text.toString().isDigitsOnly()){
                flightsLongerValue = (etFlightsLonger.text.toString().toInt() * 0.33).toInt()
            }

            flightsTotal = flightsShorterValue + flightsLongerValue

            // Total

            total = homeTotal + carMotorbikeTotal + publicTransportTotal + flightsTotal

            // Kreiranje na bundle i vrakjanje nazad vo MainActivity

            val intent = Intent(this, MainActivity::class.java)
            val data = Bundle()
            data.putString("electricity", electricityValue.toString())
            data.putString("natural_gas", naturalGasValue.toString())
            data.putString("coal", coalValue.toString())
            data.putString("wooden_pellets", woodenPelletsValue.toString())
            data.putString("total", total.toString())
            data.putString("transport_total", publicTransportTotal.toString())
            data.putString("flights_total", flightsTotal.toString())
            data.putString("car_motorbike_total", carMotorbikeTotal.toString())
            intent.putExtra("key", data)
            startActivity(intent)
        }
    }
}