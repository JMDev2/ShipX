package com.ekenya.rnd.dashboard.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.model.ShipResponseItem
import com.ekenya.rnd.common.utils.Status
import com.ekenya.rnd.dashboard.databinding.FragmentShipDetailsBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ShipDetailsFragment : BaseDaggerFragment() {

    private lateinit var binding: FragmentShipDetailsBinding

    private val args: ShipDetailsFragmentArgs by navArgs()


    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShipDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeShipDetails()

    }

    private fun observeShipDetails() {
        val ship = requireArguments().getParcelable<ShipResponseItem>("item")
        ship?.let {
            Picasso.get().load(ship.image).into(binding.shipImg)
            binding.shipTitleTv.text = ship.ship_name
            binding.shipModelTv.text = ship.ship_type ?: "0"
            binding.shipSpeedTv.text = ship.speed_kn?.toString() ?: "0"
            // binding.shipSummaryTv.text = ship.

            binding.shipTypeTv.text = ship.ship_type?.toString() ?: "0"

            binding.shipWeightTv.text = ship.weight_kg?.toString() ?: "0"

        }
    }

//    private fun observeShipDetails() {
//        viewModel.getShips()
//        viewModel.observeShipLiveData().observe(
//            viewLifecycleOwner
//        ){
//            ships ->
//            when(ships.status){
//                Status.SUCCESS ->{
//                    val response = ships.data
//
//                    response.let {
//                        var response = ships.data?.find { ship ->
//                            ship.ship_id == shipId
//                        }
//
//                        binding.shipTitleTv.text = response?.ship_name
//                        Picasso.get().load(response?.image).into(binding.shipImg)
//                        Log.d("main","iamge ${response?.image}")
//                    }
//                }
//                Status.LOADING -> {
//
//                }
//                Status.ERROR ->{
//
//                }
//            }
//
//
//        }
//    }


}