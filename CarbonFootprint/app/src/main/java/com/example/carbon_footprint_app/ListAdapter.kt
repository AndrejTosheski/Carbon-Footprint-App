package com.example.carbon_footprint_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListAdapter(private val context: Context, private var itemList: List<String>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val imageViewArrow: ImageView = itemView.findViewById(R.id.imageViewArrow)
        val imageViewItem: ImageView = itemView.findViewById(R.id.imageViewItem)
    }

    fun updateList(newItemList: List<String>) {
        itemList = newItemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.textViewTitle.text = item

        val modified = item.lowercase().replace(" ", "_")

        val resourceId = holder.itemView.context.resources.getIdentifier(modified, "drawable", holder.itemView.context.packageName)

        holder.imageViewItem.setImageResource(resourceId)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, TipsActivity::class.java)
            intent.putExtra("item", item)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}