package com.ekenya.rnd.dashboard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.dashboard.R
import com.ekenya.rnd.dashboard.databinding.FragmentFavouritesBinding
import com.ekenya.rnd.dashboard.databinding.FragmentShipDetailsBinding

class FavouritesFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentFavouritesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        return binding.root
    }


}