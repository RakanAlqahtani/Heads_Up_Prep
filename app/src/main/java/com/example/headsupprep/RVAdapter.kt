package com.example.headsupprep

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.headsupprep.Model.Celebrity
import kotlinx.android.synthetic.main.item_row.view.*

class RVAdapter(var celebrity : Celebrity) : RecyclerView.Adapter<RVAdapter.ItemHolderView>() {
    class ItemHolderView(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolderView {
        return ItemHolderView(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolderView, position: Int) {
        var text = celebrity[position]
        holder.itemView.apply {
          tvName.text  =  text.name
            tvTaboo1.text = text.taboo1
             tvTaboo2.text= text.taboo2
            tvTaboo3.text = text.taboo3

        }
    }

    override fun getItemCount() = celebrity.size

    fun update(celebrity: Celebrity) {
        this.celebrity = celebrity
        notifyDataSetChanged()
    }
}