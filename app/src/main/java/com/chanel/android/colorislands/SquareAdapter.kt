package com.chanel.android.colorislands

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chanel.android.colorislands.databinding.SquareBinding

class SquareAdapter(
    private var squares: List<Square>
) : RecyclerView.Adapter<SquareAdapter.SquareViewHolder>() {

    var onItemClick: ((Square) -> Unit)? = null

    inner class SquareViewHolder(val binding: SquareBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquareViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SquareBinding.inflate(layoutInflater, parent, false)
        return SquareViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SquareViewHolder, position: Int) {
        holder.binding.apply {
            square.setBackgroundColor(squares[position].color)
            square.setOnClickListener {
                onItemClick?.invoke(squares[position])
            }
        }
    }

    override fun getItemCount(): Int {
       return squares.size
    }

}