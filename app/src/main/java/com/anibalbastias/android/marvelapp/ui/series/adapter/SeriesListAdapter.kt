package com.anibalbastias.android.marvelapp.ui.series.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.series.model.SeriesItemData
import com.anibalbastias.android.marvelapp.databinding.ViewItemSeriesBinding
import com.anibalbastias.android.marvelapp.ui.series.adapter.viewholder.SeriesListItemViewHolder

class SeriesListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: MutableList<SeriesItemData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = ViewItemSeriesBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesListItemViewHolder(binding)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holderItem = holder as? SeriesListItemViewHolder
        holderItem?.bind(items, position)
    }
}