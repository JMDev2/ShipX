package com.ekenya.rnd.dashboard.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.model.ShipResponseItem
import com.ekenya.rnd.common.utils.Status
import com.ekenya.rnd.dashboard.MainActivity
import com.ekenya.rnd.dashboard.R
import com.ekenya.rnd.dashboard.adapter.ShipAdapter
import com.ekenya.rnd.dashboard.databinding.FragmentHomeBinding
import javax.inject.Inject


class HomeFragment : BaseDaggerFragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var shipAdapter: ShipAdapter

    var filteredShips: List<ShipResponseItem> = ArrayList()

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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeShips()

        //perfoming search
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Perform filtering or live search as the user types
                filterShip(newText)
                return true
            }
        })

    }

    private fun filterShip(ship: String) {
        val filteredList =
            filteredShips.filter { it.ship_name?.contains(ship, ignoreCase = true) ?: false }
        val theFilteredShips = ArrayList(filteredList)

        if (theFilteredShips.isEmpty()){
           // binding.errorLayout.visibility = View.VISIBLE
        } else {
           // binding.errorLayout.visibility = View.GONE
        }

        shipAdapter = ShipAdapter(theFilteredShips)
        binding.recyclerView.adapter = shipAdapter
        shipAdapter.notifyDataSetChanged()
    }


    private fun setRecyclerView(){
        binding.recyclerView.apply{
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = shipAdapter
        }
    }

    private fun observeShips() {
        viewModel.observeShipLiveData().observe(viewLifecycleOwner) { shipResponse ->
            when(shipResponse.status){
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE

                    val ship = shipResponse.data
                    Log.d("kim", "observeParking: ${shipResponse.data}")

                    ship?.let {
                        filteredShips = ship
                        shipAdapter = ShipAdapter(it)
                        setRecyclerView()
                    }
                }
                Status.ERROR -> {

                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }

        }
    }



}