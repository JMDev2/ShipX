package com.ekenya.rnd.dashboard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.model.ShipResponseItem
import com.ekenya.rnd.common.utils.Status
import com.ekenya.rnd.dashboard.adapter.ShipDataAdapter
import com.ekenya.rnd.dashboard.database.ShipDataViewModel
import com.ekenya.rnd.dashboard.databinding.FragmentFavouritesBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouritesFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var shipDataAdapter: ShipDataAdapter

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, factory)[ShipDataViewModel::class.java]
    }

    // Initialize an empty list for ship data
    val shipData: ArrayList<ShipResponseItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeSavedship()

    }

    // Initialize your RecyclerView and adapter here
    private fun setRecycler() {
        binding.recyclerView1.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = shipDataAdapter
        }
    }
//
    private fun observeSavedship() {
    // Observe ship data from ViewModel
    lifecycleScope.launch {
        viewModel.allShips.collect { ship ->
            when (ship.status) {
                Status.SUCCESS -> {
                    //progressbar

                    val response = ship.data
                    response?.let {
                        shipDataAdapter = ShipDataAdapter(it)
                        setRecycler()
                    }
                }
                Status.ERROR -> {

                }
                Status.LOADING -> {

                }
            }
        }
    }

}
}
