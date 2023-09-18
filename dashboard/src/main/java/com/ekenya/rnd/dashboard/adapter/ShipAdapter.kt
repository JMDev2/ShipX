package com.ekenya.rnd.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.rnd.common.model.ShipResponseItem
import com.ekenya.rnd.dashboard.databinding.ShiplayoutBinding
import com.squareup.picasso.Picasso

class ShipAdapter(private val ships : ArrayList<ShipResponseItem>):
RecyclerView.Adapter<ShipAdapter.ShipsViewHolder>(){

    inner class ShipsViewHolder(val binding: ShiplayoutBinding, val context: Context): RecyclerView.ViewHolder(binding.root){
        fun bind(
            ship : ShipResponseItem
        ){
            binding.apply {
                Picasso.get().load(ship.image).into(binding.shipImage)
                title.text = ship.ship_name
                shipType.text = ship.ship_type
              //  Picasso.get().load(ship.active).into(binding.activeImg)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipsViewHolder {
        return ShipsViewHolder(
            ShiplayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent.context
        )
    }

    override fun onBindViewHolder(holder: ShipsViewHolder, position: Int) {
        holder.bind(ships[position])

    }

    override fun getItemCount(): Int = ships.size
}
