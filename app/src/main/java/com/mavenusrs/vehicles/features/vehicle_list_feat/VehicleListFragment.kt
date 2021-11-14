package com.mavenusrs.vehicles.features.vehicle_list_feat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mavenusrs.vehicles.R
import com.mavenusrs.vehicles.databinding.FragmentVehicleListBinding
import com.mavenusrs.vehicles.domain.model.Vehicle
import com.mavenusrs.vehicles.features.common.StatefulResource
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [VehicleListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class VehicleListFragment : Fragment() {

    lateinit var binding: FragmentVehicleListBinding
    private val vehicleListViewModel: VehicleListViewModel by viewModels()

    private lateinit var vehicleAdapter: VehicleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVehicleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        setupView()

        loadData()
    }

    private fun loadData() {
        vehicleListViewModel.getVehicles()
    }

    private fun setupView() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        vehicleAdapter = VehicleAdapter()
        vehicleAdapter.setHasStableIds(true)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.recycleChildrenOnDetach = true

        val vehicleRecyclerView = binding.rvVehicles
        with(vehicleRecyclerView) {
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    linearLayoutManager.orientation
                )
            )
            itemAnimator = DefaultItemAnimator()
            layoutManager = linearLayoutManager
            adapter = vehicleAdapter
        }
    }

    private fun observe() {
        vehicleListViewModel.vehicleLiveData.observe(viewLifecycleOwner) {
            when (it?.status) {
                StatefulResource.Status.LOADING -> {
                    showLoading()
                }
                StatefulResource.Status.SUCCESSES -> onVehiclesLoaded(it.data)
                StatefulResource.Status.IS_EMPTY -> showEmpty()
                StatefulResource.Status.FAILED -> onError(it.errorMessage, it.throwable)
            }
        }
    }

    private fun onError(errorMessage: String?, throwable: Throwable?) {
        hideLoading()
        Toast.makeText(
            requireContext(),
            errorMessage ?: throwable?.message ?: getString(R.string.general_error_message),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showEmpty() {
        hideLoading()
        binding.tvEmptyMsg.visibility = View.VISIBLE
    }

    private fun onVehiclesLoaded(data: List<Vehicle>?) {
        Log.d("VehicleListFragment", "onVehiclesLoaded${data?.get(1)?.notes?.get(0)}")
        hideLoading()
        binding.tvEmptyMsg.visibility = View.GONE
        binding.rvVehicles.visibility = View.VISIBLE

        data?.apply {
            vehicleAdapter.submitList(data.map { it.copy() })
        }
    }

    private fun showLoading() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.pbLoading.visibility = View.GONE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment VehicleListFragment.
         */
        @JvmStatic
        fun newInstance() = VehicleListFragment()
    }
}