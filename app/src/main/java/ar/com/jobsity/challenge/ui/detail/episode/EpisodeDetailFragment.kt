package ar.com.jobsity.challenge.ui.detail.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ar.com.jobsity.challenge.databinding.FragmentEpisodeDetailBinding
import ar.com.jobsity.challenge.utils.extensions.removeHtmlTags
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeDetailFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeDetailBinding
    private val args: EpisodeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = args.episode.name
        args.episode.apply {
            binding.episodeDetailImage.setImageURI(this.image?.original)
            binding.episodeName.text = this.name
            binding.episodeNumber.text = this.number.toString()
            binding.seasonNumber.text = this.season.toString()
            binding.summary.text = this.summary.removeHtmlTags()
        }
    }
}