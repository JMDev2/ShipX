package com.ekenya.rnd.dashboard.ui.home

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.model.ShipData
import com.ekenya.rnd.common.model.ShipResponseItem
import com.ekenya.rnd.common.utils.toast
import com.ekenya.rnd.dashboard.R
import com.ekenya.rnd.dashboard.database.ShipDataViewModel
import com.ekenya.rnd.dashboard.databinding.FragmentShipDetailsBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ShipDetailsFragment : BaseDaggerFragment() {

    private lateinit var binding: FragmentShipDetailsBinding




    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, factory)[ShipDataViewModel::class.java]
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

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        // Enable the back arrow in the toolbar
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Set the click listener for the back arrow
        binding.toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favView.setOnClickListener {
            findNavController().navigate(R.id.favouritesFragment)
        }


        val ship = ShipData()
        binding.fav.setOnClickListener {
            viewModel.saveShip(ship)
        }

        observeShipDetails()
    }

    private fun observeShipDetails() {
        val ship = requireArguments().getParcelable<ShipResponseItem>("item")
        ship?.let {

            Picasso.get().load(ship.image).into(binding.shipImg)
            if (ship.image != null){
                Picasso.get().load(ship.image).into(binding.shipImg)
            }else{
                binding.shipImg.setImageResource(R.drawable.ship)
            }

            //ship title with underline
            val shipName = ship.ship_name
            val spannableString = SpannableString(shipName)
            spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, 0)

            binding.shipTitleTv.text = spannableString
            binding.shipModelTv.text = ship.ship_type ?: "0"
            binding.shipSpeedTv.text = ship.speed_kn?.toString() ?: "0"
            binding.shipTypeTv.text = ship.ship_type?.toString() ?: "0"

            binding.shipWeightTv.text = ship.weight_kg?.toString() ?: "0"



        }
    }

}