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
import com.google.android.material.snackbar.Snackbar
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
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.retryBtn?.setOnClickListener {
            doRefresh()
        }
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
            binding.noshipText.visibility = View.VISIBLE
        } else {
           // binding.errorLayout.visibility = View.GONE
            binding.noshipText.visibility = View.GONE
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
                    hideProgressBar()
                    showNetworkErrorImage()
                    val ship = shipResponse.data
                    Log.d("kim", "observeParking: ${shipResponse.data}")

                    ship?.let {
                        hideNetworkErrorImage()
                        filteredShips = ship
                        shipAdapter = ShipAdapter(it)
                        setRecyclerView()
                    }
                }
                Status.ERROR -> {
                    hideProgressBar()
                    showNetworkErrorImage()
                    binding?.root?.let { showSnackBar(it) }

                }
                Status.LOADING -> {
                    showProgressBar()
                }
            }

        }
    }

    private fun showSnackBar(view: View){
        Snackbar.make(
            view,
            "Error loading ships",
            Snackbar.LENGTH_LONG
        )
            .setAction("Retry"){
                doRefresh()
            }.show()
    }

    private fun doRefresh() {
        viewModel.refresh()
        hideProgressBar()
        hideNetworkErrorImage()
        observeShips()
    }

    private fun showProgressBar() {
        binding?.progressBar?.visibility = View.VISIBLE
    }

    private fun showNetworkErrorImage() {
        binding?.eorImage?.visibility = View.VISIBLE
        binding?.retryBtn?.visibility = View.VISIBLE

    }
    private fun hideNetworkErrorImage() {
        binding?.eorImage?.visibility = View.GONE
        binding?.retryBtn?.visibility = View.GONE

    }

    private fun hideProgressBar() {
        binding?.progressBar?.visibility = View.GONE
    }

}