package com.ekenya.rnd.dashboard.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.rnd.common.model.ShipResponseItem
import com.ekenya.rnd.dashboard.R
import com.ekenya.rnd.dashboard.databinding.ShiplayoutBinding
import com.squareup.picasso.Picasso

class ShipAdapter(private val ships : ArrayList<ShipResponseItem>):
RecyclerView.Adapter<ShipAdapter.ShipsViewHolder>(){

    var onItemClick: ((String) -> Unit) = {}




    inner class ShipsViewHolder(val binding: ShiplayoutBinding, val context: Context): RecyclerView.ViewHolder(binding.root){
        fun bind(
            ship : ShipResponseItem
        ){
            binding.apply {

                if (ship.image != null && ship.image.isNotEmpty()) {
                    Picasso.get().load(ship.image).into(binding.shipImage)
                } else {
                    shipImage.setImageResource(R.drawable.ship)
                }

                title.text = ship.ship_name
                shipType.text = "Built: ${ship.ship_type.toString()}"
              if (ship.active == true){
                  activeImg.setImageResource(R.drawable.red)
              }else{
                  activeImg.setImageResource(R.drawable.star)
              }
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

        holder.itemView.setOnClickListener {
            Log.d("ClickEvent", "Item clicked: ${ships[position].ship_id}")
            onItemClick.invoke(ships[position].ship_id)
        }


    }

    override fun getItemCount(): Int = ships.size
}

