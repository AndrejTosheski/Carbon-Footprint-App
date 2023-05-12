package com.example.carbon_footprint_app.ui.measure

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RotateDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.carbon_footprint_app.CalculateActivity
import com.example.carbon_footprint_app.R
import com.example.carbon_footprint_app.databinding.FragmentMeasureBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MeasureFragment : Fragment() {

    private var electricityValue = ""
    private var naturalGasValue = ""
    private var coalValue = ""
    private var woodenPelletsValue = ""
    private var publicTransportTotal = ""
    private var flightsTotal = ""
    private var carMotorbikeTotal = ""
    private var total = ""

    private var _binding: FragmentMeasureBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(MeasureViewModel::class.java)

        _binding = FragmentMeasureBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if(arguments != null){
            electricityValue = arguments?.getString("electricity").toString()
            naturalGasValue = arguments?.getString("natural_gas").toString()
            coalValue = arguments?.getString("coal").toString()
            woodenPelletsValue = arguments?.getString("wooden_pellets").toString()
            publicTransportTotal = arguments?.getString("transport_total").toString()
            flightsTotal = arguments?.getString("flights_total").toString()
            carMotorbikeTotal = arguments?.getString("car_motorbike_total").toString()
            total = arguments?.getString("total").toString()
        }

        homeViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Deklariranje na promenlivi
    private lateinit var totalProgress: ProgressBar
    private lateinit var tvProgressInfo: TextView
    private lateinit var tvIsProgressGood: TextView
    private lateinit var tvElectricity: TextView
    private lateinit var tvNaturalGas: TextView
    private lateinit var tvCoal: TextView
    private lateinit var tvWoodenPellets: TextView
    private lateinit var tvCarMotorbike: TextView
    private lateinit var tvPublicTransport: TextView
    private lateinit var tvFlights: TextView
    private lateinit var btnCalculate: FloatingActionButton

    private lateinit var electricityProgressBar: ProgressBar
    private lateinit var coalProgressBar: ProgressBar
    private lateinit var woodenPelletsProgressBar: ProgressBar
    private lateinit var naturalGasProgressBar: ProgressBar
    private lateinit var transportProgressBar: ProgressBar
    private lateinit var flightsProgressBar: ProgressBar
    private lateinit var carMotorbikeProgressBar: ProgressBar


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Definiranje na promenlivi
        totalProgress = view.findViewById(R.id.totalProgress)
        tvProgressInfo = view.findViewById(R.id.textViewTotalProgress)
        tvIsProgressGood = view.findViewById(R.id.textViewProgressInfo)
        tvElectricity = view.findViewById(R.id.textviewElectricityInfo)
        tvNaturalGas = view.findViewById(R.id.textviewNaturalGasInfo)
        tvCoal = view.findViewById(R.id.textviewCoalInfo)
        tvWoodenPellets = view.findViewById(R.id.textviewWoodenPelletsInfo)
        tvCarMotorbike = view.findViewById(R.id.textviewCarMotorbikeInfo)
        tvPublicTransport = view.findViewById(R.id.textviewPublicTransportInfo)
        tvFlights = view.findViewById(R.id.textviewFlightsInfo)
        btnCalculate = view.findViewById(R.id.btnCalculate)

        electricityProgressBar = view.findViewById(R.id.progressBarElectricity)
        coalProgressBar = view.findViewById(R.id.progressBarCoal)
        woodenPelletsProgressBar = view.findViewById(R.id.progressBarWoodenPellets)
        naturalGasProgressBar = view.findViewById(R.id.progressBarNaturalGas)
        transportProgressBar = view.findViewById(R.id.progressBarPublicTransport)
        flightsProgressBar = view.findViewById(R.id.progressBarFlights)
        carMotorbikeProgressBar = view.findViewById(R.id.progressBarCarMotorbike)

        tvProgressInfo.text = total + "kg"
        tvElectricity.text = electricityValue + "kg"
        tvNaturalGas.text = naturalGasValue + "kg"
        tvCoal.text = coalValue + "kg"
        tvWoodenPellets.text = woodenPelletsValue + "kg"
        tvCarMotorbike.text = carMotorbikeTotal + "kg"
        tvPublicTransport.text = publicTransportTotal + "kg"
        tvFlights.text = flightsTotal + "kg"

        if(total.isDigitsOnly() && total != ""){
            if(total.toInt() <= 2000){
                tvIsProgressGood.text = "Your carbon emission is good!"

                val progressDrawable = totalProgress.progressDrawable as LayerDrawable
                val progressItem = progressDrawable.getDrawable(1) as RotateDrawable
                val shapeDrawable = progressItem.drawable as GradientDrawable
                shapeDrawable.setColor(ContextCompat.getColor(requireContext(), R.color.purple_500))

                totalProgress.progressDrawable = progressDrawable
            }
            else if(total.toInt() >= 2000 && total.toInt() <= 5000){
                tvIsProgressGood.text = "Your carbon emission is fair!"

                val progressDrawable = totalProgress.progressDrawable as LayerDrawable
                val progressItem = progressDrawable.getDrawable(1) as RotateDrawable
                val shapeDrawable = progressItem.drawable as GradientDrawable
                shapeDrawable.setColor(ContextCompat.getColor(requireContext(), R.color.Yellow))

                totalProgress.progressDrawable = progressDrawable
            }
            else if(total.toInt() > 5000){
                tvIsProgressGood.text = "Your carbon emission is bad!"

                val progressDrawable = totalProgress.progressDrawable as LayerDrawable
                val progressItem = progressDrawable.getDrawable(1) as RotateDrawable
                val shapeDrawable = progressItem.drawable as GradientDrawable
                shapeDrawable.setColor(ContextCompat.getColor(requireContext(), R.color.Red))

                totalProgress.progressDrawable = progressDrawable
            }

            totalProgress.progress = total.toInt()
        }

        if(electricityValue != "" && electricityValue.isDigitsOnly()){
            electricityProgressBar.progress = electricityValue.toInt()
        }

        if(naturalGasValue != "" && naturalGasValue.isDigitsOnly()){
            naturalGasProgressBar.progress = naturalGasValue.toInt()
        }

        if(coalValue != "" && coalValue.isDigitsOnly()){
            coalProgressBar.progress = coalValue.toInt()
        }

        if(woodenPelletsValue != "" && woodenPelletsValue.isDigitsOnly()){
            woodenPelletsProgressBar.progress = woodenPelletsValue.toInt()
        }

        if(publicTransportTotal != "" && publicTransportTotal.isDigitsOnly()){
            transportProgressBar.progress = publicTransportTotal.toInt()
        }

        if(flightsTotal != "" && flightsTotal.isDigitsOnly()){
            flightsProgressBar.progress = flightsTotal.toInt()
        }

        if(carMotorbikeTotal != "" && carMotorbikeTotal.isDigitsOnly()){
            carMotorbikeProgressBar.progress = carMotorbikeTotal.toInt()
        }

        btnCalculate.setOnClickListener(){
            val intent = Intent(activity, CalculateActivity::class.java)
            startActivity(intent)
        }
    }
}