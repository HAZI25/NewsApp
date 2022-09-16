package com.example.newsapp.presentation.search_news

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.newsapp.NewsApp
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import com.example.newsapp.presentation.NewsViewModel
import com.example.newsapp.presentation.ViewModelFactory
import com.example.newsapp.presentation.adapter.NewsAdapter
import com.example.newsapp.presentation.model.Article
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchNewsFragment : Fragment(), NewsAdapter.OnItemClickListener {

    private var _binding: FragmentSearchNewsBinding? = null
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

        _binding = FragmentSearchNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        observeViewModel()
        setupGalleryMenu()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.newsResult.collect {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter
        adapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
        }
    }

    private fun setupGalleryMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search_news, menu)

                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            binding.recyclerView.scrollToPosition(0)
                            viewModel.searchNews(query)
                            searchView.clearFocus()
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(article: Article) {
        val action =
            SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }
}