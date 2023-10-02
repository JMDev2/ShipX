package com.ekenya.rnd.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.rnd.common.model.ShipData
import com.ekenya.rnd.dashboard.R
import com.ekenya.rnd.dashboard.databinding.FragmentFavouritesBinding
import com.ekenya.rnd.dashboard.databinding.ShiplayoutBinding
import com.squareup.picasso.Picasso

class ShipDataAdapter(var shipData: List<ShipData>):
    RecyclerView.Adapter<ShipDataAdapter.ShipViewHolder>(){


    inner class ShipViewHolder(val binding: ShiplayoutBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ship: ShipData){
            binding.apply {

                if (ship.image != null && ship.image!!.isNotEmpty()) {
                    Picasso.get().load(ship.image).into(binding.shipImage)
                } else {
                    shipImage.setImageResource(R.drawable.ship)
                }

                title.text = ship.ship_name
                binding.shipYear.text = "Built: ${ship.year_built ?: 0}"

//                if (ship.active == true){
//                    activeImg.setImageResource(R.drawable.red)
//                }else{
//                    activeImg.setImageResource(R.drawable.star)
//                }
            }
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
