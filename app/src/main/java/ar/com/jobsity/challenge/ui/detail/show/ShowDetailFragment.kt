package ar.com.jobsity.challenge.ui.detail.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.jobsity.challenge.R
import ar.com.jobsity.challenge.databinding.FragmentShowDetailBinding
import ar.com.jobsity.challenge.network.response.Episode
import ar.com.jobsity.challenge.network.response.Season
import ar.com.jobsity.challenge.network.response.toTriple
import ar.com.jobsity.challenge.ui.views.adapters.EpisodeAdapter
import ar.com.jobsity.challenge.utils.HorizontalSpaceItemDecoration
import ar.com.jobsity.challenge.utils.extensions.px
import ar.com.jobsity.challenge.utils.extensions.removeHtmlTags
import ar.com.jobsity.challenge.utils.spinner.SpinnerDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowDetailFragment : Fragment() {

    private lateinit var binding: FragmentShowDetailBinding
    private val viewModel: ShowDetailViewModel by viewModels()
    private val args: ShowDetailFragmentArgs by navArgs()
    private val episodeAdapter = EpisodeAdapter {
        navigateToDetailEpisode(it)
    }
    private var seasons: List<Season> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = args.show.name

        args.show.apply {
            binding.showDetailImage.setImageURI(this.image?.original)
            binding.showTitle.text = this.name
            binding.genres.text = this.genres.joinToString()
            binding.schedule.text = "${this.schedule.days.joinToString()} - ${this.schedule.time}hs"
            binding.summary.text = this.summary.removeHtmlTags()
        }
        binding.episodeList.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.episodeList.addItemDecoration(HorizontalSpaceItemDecoration(8.px), 0)
        binding.episodeList.adapter = episodeAdapter

        binding.seasonButton.setOnClickListener {
            showSeasonsDialog()
        }
        setUpObservers()
    }

    private fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.seasonsUiState.flowWithLifecycle(lifecycle).collect {
                        when (it) {
                            is SeasonsUiState.Loading -> {
                                //binding.loadingView.visibility = View.VISIBLE
                            }

                            is SeasonsUiState.Error -> {
                                //binding.loadingView.visibility = View.GONE
                                showError(it.message)
                            }

                            is SeasonsUiState.Success -> {
                                //binding.loadingView.visibility = View.GONE
                                //showAdapter.refresh(it.showList)
                                seasons = it.seasonList
                                if (it.seasonList.isNotEmpty()) {
                                    binding.seasonButton.text =
                                        "${resources.getString(R.string.season)} ${it.seasonList.first().number}"
                                }
                            }
                        }
                    }
                }
                launch {
                    viewModel.episodesUiState.flowWithLifecycle(lifecycle).collect {
                        when (it) {
                            is EpisodesUiState.Loading -> {
                                //binding.loadingView.visibility = View.VISIBLE
                            }

                            is EpisodesUiState.Error -> {
                                //binding.loadingView.visibility = View.GONE
                                showError(it.message)
                            }

                            is EpisodesUiState.Success -> {
                                //binding.loadingView.visibility = View.GONE
                                episodeAdapter.refresh(it.episodeList)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showSeasonsDialog() {
        SpinnerDialog.Builder<Season>(requireContext())
            .setTitle(R.string.seasons)
            .setOnDismissListener { dialog ->
                val items = (dialog as SpinnerDialog<Season>).selectedItems
                if (items.isNotEmpty()) {
                    onSeasonSelected(items.first())
                }
            }
            .singleSelection(seasons.map { it.toTriple() })
            .setDismissAfterSelect(true)
            .create().show()
    }

    private fun onSeasonSelected(season: Season) {
        binding.seasonButton.text = "${resources.getString(R.string.season)} ${season.number}"
        viewModel.getEpisodesFromSeason(season.id)
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

    private fun navigateToDetailEpisode(episode: Episode) {
        findNavController().navigate(ShowDetailFragmentDirections.goToEpisodeDetailFragment(episode))
    }
}