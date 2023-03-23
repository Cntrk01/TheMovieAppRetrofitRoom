package com.movieapp.myapplication.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.movieapp.myapplication.R
import com.movieapp.myapplication.adapter.MovieLoadStateAdapter
import com.movieapp.myapplication.databinding.FragmentMovieBinding
import com.movieapp.myapplication.adapter.MoviePagingAdapter
import com.movieapp.myapplication.adapter.OnItemClickListener
import com.movieapp.myapplication.data.remote.Movie
import com.movieapp.myapplication.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() , OnItemClickListener{
    private val viewModel : MovieViewModel by viewModels()
    private var _binding : FragmentMovieBinding ?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMovieBinding.inflate(layoutInflater)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter= MoviePagingAdapter(this)
        binding.apply {
            rvMovie.setHasFixedSize(true)
            rvMovie.adapter=adapter.withLoadStateHeaderAndFooter(
                footer = MovieLoadStateAdapter{adapter.retry()},
                header = MovieLoadStateAdapter{adapter.retry()}
            )
        }

        val menuHost : MenuHost=requireActivity()
        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
                val searchItem=menu.findItem(R.id.action_search)
                // cast ederken hata veriyor menu sayfasında bu olcak
                //app:actionViewClass="androidx.appcompat.widget.SearchView"
                val searchView=searchItem.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if(query !=null){
                            binding.rvMovie.scrollToPosition(0)
                            viewModel.searchMovie(query)
                            searchView.clearFocus()
                        }
                        return false
                    }
                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        },viewLifecycleOwner,Lifecycle.State.RESUMED) //menu butonunu tekrar tekrar ekliyordu.Bu kodu ekleyince tekrarlamayı engelledi.


        with(viewModel){
            observeData.observe(viewLifecycleOwner){
                adapter.submitData(viewLifecycleOwner.lifecycle,it)
            }
        }
    }

    override fun onItemClick(movie: Movie) {
        val action=MovieFragmentDirections.actionNavMovieToDetailFragment(movie)
        findNavController().navigate(action)
    }


}