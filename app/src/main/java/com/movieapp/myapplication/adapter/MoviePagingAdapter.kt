package com.movieapp.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.movieapp.myapplication.R
import com.movieapp.myapplication.data.remote.Movie
import com.movieapp.myapplication.databinding.ItemMovieBinding

class MoviePagingAdapter(private val onItemClickListener: OnItemClickListener)
    : PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHolder>(COMPARATOR) {

    companion object{
        private val COMPARATOR=object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return  oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
               return  oldItem==newItem
            }

        }
    }



    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                val position=bindingAdapterPosition
                if(position !=RecyclerView.NO_POSITION){
                    val item=getItem(position)
                    if(item !=null){
                        onItemClickListener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(movie: Movie){
            with(binding){
                Glide.with(itemView).load("${movie.baseUrl}${movie.poster_path}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(moviePoster)

                tvMovieTitle.text=movie.original_title
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem=getItem(position)
        if(currentItem !=null){
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding=ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding)
    }


}