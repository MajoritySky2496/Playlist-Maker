package com.soundhaven.app.playlist.mediateca.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soundhaven.app.databinding.FragmentSelectedtracksBinding
import com.soundhaven.app.playlist.main.app.App
import com.soundhaven.app.playlist.mediateca.presentation.SelectedTracksViewModel
import com.soundhaven.app.playlist.mediateca.presentation.model.SelectedTrackState
import com.soundhaven.app.playlist.player.ui.PlayerActivity
import com.soundhaven.app.playlist.search.domain.models.Track
import com.soundhaven.app.playlist.search.ui.tracks.TrackAdapter
import com.soundhaven.app.playlist.util.BindingFragment
import com.soundhaven.app.playlist.util.NavigationRouter.Companion.REQUEST_IS_FAVORITE
import javax.inject.Inject

class SelectedTracksFragment : BindingFragment<FragmentSelectedtracksBinding>() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var recyclerView: RecyclerView
    private val adapter = TrackAdapter {
    }
    lateinit var viewModel: SelectedTracksViewModel
    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.getSelectedTrack()
            }
        }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSelectedtracksBinding {
        return FragmentSelectedtracksBinding.inflate(inflater, container, false)
    }

    companion object {
        fun newInstance() = SelectedTracksFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SelectedTracksViewModel::class.java)
        recyclerView = binding.recyclerViewSelected
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        viewModel.observeState().observe(requireActivity()) { render(it) }
        viewModel.getSelectedTrack()

        adapter.onItemClick = {
            val intent = Intent(activity, PlayerActivity::class.java)
            intent.putExtra(Track::class.java.simpleName, it)
            startForResult.launch(intent)

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as App).appComponent.injectSelectedTracksFragment(this)
    }

    fun render(state: SelectedTrackState) {
        when (state) {
            is SelectedTrackState.TrackContent -> showSelectedTrack(state.tracks)
            is SelectedTrackState.Error -> showError(state.errorMessage)
        }
    }

    fun showSelectedTrack(tracks: List<Track>) {
        adapter.track.clear()
        binding.imageView.visibility = View.GONE
        binding.addPlayLists.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        adapter.track.addAll(tracks.toMutableList())
        adapter.notifyDataSetChanged()

    }

    fun showError(error: String) {
        recyclerView.visibility = View.GONE
        binding.imageView.visibility = View.VISIBLE
        binding.addPlayLists.text = error
        binding.addPlayLists.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IS_FAVORITE) {
            if (requestCode == Activity.RESULT_OK) {
                viewModel.getSelectedTrack()
            }
        }
    }

}