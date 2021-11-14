package com.mavenusrs.vehicles.features.vehicle_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mavenusrs.vehicles.R
import com.mavenusrs.vehicles.domain.model.Vehicle


/**
 * A simple [Fragment] subclass.
 * Use the [VehicleDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VehicleDetailsFragment : Fragment() {
    private var vehicle: Vehicle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            vehicle = it.getString(VEHICLE_DETAILS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_details, container, false)
    }

    companion object {
        private const val VEHICLE_DETAILS = "VEHICLE_DETAILS"

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
//                    putParcelable(VEHICLE_DETAILS, vehicle)
                }
            }
    }
}