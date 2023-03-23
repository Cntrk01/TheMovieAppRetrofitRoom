package com.movieapp.myapplication.adapter

import com.movieapp.myapplication.data.remote.Movie

interface OnItemClickListener {
    fun onItemClick(movie:Movie)
}