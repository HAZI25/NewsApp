package com.example.newsapp.presentation.favourite_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.NewsApp
import com.example.newsapp.databinding.FragmentFavouriteNewsBinding
import com.example.newsapp.presentation.NewsViewModel
import com.example.newsapp.presentation.ViewModelFactory
import com.example.newsapp.presentation.model.Article
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteNewsFragment : Fragment(), FavouriteNewsAdapter.OnItemClickListener {

    private var _binding: FragmentFavouriteNewsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: NewsViewModel

    private val component by lazy {
        (activity?.application as NewsApp).component
    }

    private val adapter by lazy {
        FavouriteNewsAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        component.inject(this)

        _binding = FragmentFavouriteNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.favouriteNews.collect {
                adapter.differ.submitList(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter

        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder,
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.bindingAdapterPosition
                    val article = adapter.differ.currentList[position]
                    viewModel.deleteArticle(article)
                    Snackbar.make(requireView(),
                        "Article deleted successfully",
                        Snackbar.LENGTH_LONG
                    ).apply {
                        setAction("Undo") {
                            viewModel.saveArticle(article)
                        }
                        show()
                    }
                }
            }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerView)
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
    }

    override fun onItemClick(article: Article) {
        val action =
            FavouriteNewsFragmentDirections.actionFavouriteNewsFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}