package com.movieapp.myapplication.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.movieapp.myapplication.R
import com.movieapp.myapplication.databinding.FragmentDetailBinding
import com.movieapp.myapplication.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding : FragmentDetailBinding ?=null
    private val binding get() = _binding!!
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentDetailBinding.inflate(layoutInflater)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val movie=args.movie
            Glide.with(requireContext())
                //Burada 1 tane boşluk vardı url ile poster path arasında ondan dolayı detay yüklenmiyor onLoadFail çalışıyordu dikkat !
                .load("${movie.baseUrl}${movie.poster_path}")
                .error(R.drawable.ic_error)
                //burada listener bize drawable yüklenmene kadar progresbar gösteriyor görsel hazır olunca da görseli gösteriyor.
                .listener(object  : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible=false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible=false
                        tvDescription.isVisible=true
                        tvMovieTitle.isVisible=true
                        return false
                    }

                }).into(ivMoviePoster)

            var isChechked=false
            CoroutineScope(Dispatchers.IO).launch {
                val count=viewModel.checkMovie(movie.id)
                withContext(Dispatchers.Main){
                    if(count>0){
                        toogleFavorite.isChecked=true
                        isChechked=true
                    }else{
                        toogleFavorite.isChecked=false
                        isChechked=false
                    }
                }
            }

            toogleFavorite.setOnClickListener {
                isChechked=!isChechked
                if(isChechked){
                    viewModel.addToFavorite(movie)
                    Toast.makeText(requireContext(),"Add Favorite Movie",Toast.LENGTH_LONG).show()
                }else{
                    viewModel.removeFromList(movie.id)
                    Toast.makeText(requireContext(),"Remove Favorite Movie",Toast.LENGTH_LONG).show()
                }
                toogleFavorite.isChecked=isChechked
            }

            tvDescription.text=movie.overview
            tvMovieTitle.text=movie.original_title
        }
    }


}