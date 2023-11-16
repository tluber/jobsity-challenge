package ar.com.jobsity.challenge.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ar.com.jobsity.challenge.R
import ar.com.jobsity.challenge.databinding.FragmentSearchBinding
import ar.com.jobsity.challenge.network.response.Show
import ar.com.jobsity.challenge.ui.views.adapters.ShowAdapter
import ar.com.jobsity.challenge.utils.decoration.GridSpacingItemDecoration
import ar.com.jobsity.challenge.utils.extensions.px
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val showAdapter = ShowAdapter {
        navigateToDetailShow(it)
    }
    private val columns = 3
    private var currentQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.showList.layoutManager = GridLayoutManager(this.context, columns)
        binding.showList.addItemDecoration(GridSpacingItemDecoration(columns, 8.px, true))
        binding.showList.adapter = showAdapter

        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isEmpty()) {
                        binding.resultsLayout.visibility = View.GONE
                        binding.defaultLayout.visibility = View.VISIBLE
                    }
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    currentQuery = it
                    viewModel.getShows(it)
                }
                return false
            }
        })
    }

    private fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.searchUiState.flowWithLifecycle(lifecycle).collect {
                        when (it) {
                            is SearchUiState.Loading -> {
                                binding.loadingView.visibility = View.VISIBLE
                            }

                            is SearchUiState.Error -> {
                                binding.loadingView.visibility = View.GONE
                                showError(it.message)
                            }

                            is SearchUiState.Success -> {
                                binding.loadingView.visibility = View.GONE
                                binding.resultsLayout.visibility = View.VISIBLE
                                binding.defaultLayout.visibility = View.GONE
                                if (it.showList.isNotEmpty()) {
                                    binding.resultsTextView.text =
                                        "${resources.getString(R.string.search_results)} \"$currentQuery\""
                                } else {
                                    binding.resultsTextView.text =
                                        "${resources.getString(R.string.search_results_empty)} \"$currentQuery\""
                                }
                                showAdapter.refresh(it.showList)
                            }

                            else -> {

                            }
                        }
                    }
                }
            }
        }
    }

    private fun navigateToDetailShow(show: Show) {
        findNavController().navigate(SearchFragmentDirections.goToShowDetailFragment(show))
    }

    private fun showError(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.error_title))
            .setMessage(message)
            .setPositiveButton(resources.getString(R.string.accept)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .show()
    }
}