package com.ekenya.rnd.dashboard.ui.home

import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShipDetailsFragment : BaseDaggerFragment() {

    private lateinit var binding: FragmentShipDetailsBinding
    private var i : Int = 0


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
        binding = FragmentShipDetailsBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewFav.setOnClickListener {
            findNavController().navigate(R.id.favouritesFragment)
        }

        observeShipDetails()
        tapTosave()
    }

    private fun tapTosave(){
        binding.fav.setOnClickListener {
            i++
            val handler = Handler()
            handler.postDelayed({
                if (i==1){
                    toast("Tap twice to save")
                }else if (i==2){
                    val scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_in)
                    binding.fav.startAnimation(scaleAnimation)
                    saveShip()
                    toast("Added to favourites")
                    binding.fav.setImageResource(R.drawable.favorite_saved)
                }else{
                    toast("Already Saved")
                }
            }, 500)
        }
    }

    private fun saveShip(){
        val ship = ShipData()
        lifecycleScope.launch {
            val shipExists = viewModel.checkIfShipExists(ship.id)
            Log.e("maina", "Idhgs: ${ship.id}")

            if (shipExists) {
                Toast.makeText(requireContext(), "Ship with ID ${ship.id} already exists", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.saveShip(ship)
            }
        }
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
            Log.e("maina", "Id${ship.ship_id}")

        }
    }

}