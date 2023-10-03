package com.ekenya.rnd.dashboard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.model.ShipData
import com.ekenya.rnd.common.model.ShipResponseItem
import com.ekenya.rnd.common.utils.Status
import com.ekenya.rnd.common.utils.toast
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

    private fun onItemClick(){
        shipDataAdapter.onItemClick = { shipData ->
            toast("swiped")
        }
    }

    private fun swipeToDelete(){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                shipDataAdapter.removeItem(position)
            }

        }).attachToRecyclerView(binding.recyclerView1)
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
                        shipDataAdapter = ShipDataAdapter(viewModel,it)
                        setRecycler()
                        swipeToDelete()
                       // onItemClick()
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
