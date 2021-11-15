package com.mavenusrs.vehicles.features.vehicle_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mavenusrs.vehicles.R
import com.mavenusrs.vehicles.databinding.FragmentVehicleDetailsBinding
import com.mavenusrs.vehicles.domain.model.Vehicle


/**
 * A simple [Fragment] subclass.
 * Use the [VehicleDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VehicleDetailsFragment : Fragment() {

    private lateinit var binding: FragmentVehicleDetailsBinding

    private var vehicle: Vehicle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            vehicle = it.getParcelable(VEHICLE_DETAILS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVehicleDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvVehicleDetails.text = """
                Not implemented because it is not required in the three tasks 
                
                model: ${vehicle?.make} make: ${vehicle?.model}  
                description: ${vehicle?.description ?: ""} 
                firstRegistration: ${vehicle?.firstRegistration ?: ""}
                Notes: ${vehicle?.notes?.joinToString(",") ?: ""}
                Fuel : ${vehicle?.fuel ?: ""}
            """.trimIndent()
    }

    companion object {
        const val VEHICLE_DETAILS = "VEHICLE_DETAILS"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param vehicle Parameter 1.
         * @return A new instance of fragment VehicleDetailsFragment.
         */
        @JvmStatic
        fun newInstance() =
            VehicleDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(VEHICLE_DETAILS, vehicle)
                }
            }
    }
}