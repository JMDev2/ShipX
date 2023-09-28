package com.ekenya.rnd.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.rnd.common.model.ShipData
import com.ekenya.rnd.dashboard.databinding.FragmentFavouritesBinding
import com.ekenya.rnd.dashboard.databinding.ShiplayoutBinding
import com.squareup.picasso.Picasso

class ShipDataAdapter(var shipData: List<ShipData>):
    RecyclerView.Adapter<ShipDataAdapter.ShipViewHolder>(){


    inner class ShipViewHolder(val binding: ShiplayoutBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ship: ShipData){
            binding.shipYear.text = ship.year_built.toString()
            binding.title.text = ship.ship_name
            Picasso.get().load(ship.image).into(binding.shipImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipViewHolder {
        return ShipViewHolder(
            ShiplayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent.context
        )
    }

    override fun onBindViewHolder(holder: ShipViewHolder, position: Int) {
        holder.bind(shipData[position])
    }

    override fun getItemCount(): Int = shipData.size
}
