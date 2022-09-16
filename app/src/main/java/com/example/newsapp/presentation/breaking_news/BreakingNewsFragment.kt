package com.example.newsapp.presentation.breaking_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.newsapp.NewsApp
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.presentation.NewsViewModel
import com.example.newsapp.presentation.ViewModelFactory
import com.example.newsapp.presentation.adapter.NewsAdapter
import com.example.newsapp.presentation.model.Article
import kotlinx.coroutines.launch
import javax.inject.Inject

class BreakingNewsFragment : Fragment(), NewsAdapter.OnItemClickListener {

    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: NewsViewModel

    private val component by lazy {
        (activity?.application as NewsApp).component
    }

    private val adapter by lazy {
        NewsAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        component.inject(this)

        _binding = FragmentBreakingNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        observeViewModel()
    }


    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter
        adapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.breakingNews.collect {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

    override fun onItemClick(article: Article) {
        //TODO()
    }
}