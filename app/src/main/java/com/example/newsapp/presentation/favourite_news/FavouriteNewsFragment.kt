package com.example.newsapp.presentation.favourite_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.NewsApp
import com.example.newsapp.databinding.FragmentFavouriteNewsBinding

class FavouriteNewsFragment : Fragment() {

    private var _binding: FragmentFavouriteNewsBinding? = null
    private val binding get() = _binding!!


    private val component by lazy {
        (activity?.application as NewsApp).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        component.inject(this)

        _binding = FragmentFavouriteNewsBinding.inflate(inflater)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}