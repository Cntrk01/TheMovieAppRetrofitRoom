package com.movieapp.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.movieapp.myapplication.R
import com.movieapp.myapplication.databinding.ItemMovieBinding
import com.movieapp.myapplication.local.FavoriteMovie

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){

    private lateinit var list : List<FavoriteMovie>

    fun setMovieList(list:List<FavoriteMovie>){
        this.list=list
        notifyDataSetChanged()
    }

    private var onItemClickCallBack : OnClickItemCallBack?=null
    fun setOnItemClickCallBack(onItemClickListener: OnClickItemCallBack){
        this.onItemClickCallBack=onItemClickListener
    }

    inner class FavoriteViewHolder(private val binding:ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(favoriteMovie: FavoriteMovie){
            with(binding){
                Glide.with(itemView)
                    .load("${favoriteMovie.baseUrl}${favoriteMovie.poster_path}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(moviePoster)

                tvMovieTitle.text=favoriteMovie.original_title

                binding.root.setOnClickListener {
                    onItemClickCallBack?.onItemClick(favoriteMovie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
       val binding=ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
       holder.bind(list[position])
    }

    interface  OnClickItemCallBack{
        fun onItemClick(favoriteMovie: FavoriteMovie)
    }
}