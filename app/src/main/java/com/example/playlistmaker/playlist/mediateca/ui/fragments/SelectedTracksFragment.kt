package com.example.playlistmaker.playlist.mediateca.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.FragmentSelectedtracksBinding
import com.example.playlistmaker.playlist.mediateca.presentation.SelectedTracksViewModel
import com.example.playlistmaker.playlist.mediateca.presentation.model.SelectedTrackState
import com.example.playlistmaker.playlist.player.ui.PlayerActivity
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.search.ui.tracks.TrackAdapter
import com.example.playlistmaker.playlist.util.BindingFragment
import com.example.playlistmaker.playlist.util.NavigationRouter
import com.example.playlistmaker.playlist.util.NavigationRouter.Companion.REQUEST_IS_FAVORITE
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SelectedTracksFragment : BindingFragment<FragmentSelectedtracksBinding>() {



    lateinit var recyclerView: RecyclerView
    private val adapter = TrackAdapter {
    }
    private val viewModel: SelectedTracksViewModel by viewModel {
        parametersOf()
    }
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
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
        recyclerView = binding.recyclerViewSelected
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        viewModel.observeState().observe(requireActivity()){render(it)}
        viewModel.getSelectedTrack()




        adapter.onItemClick = {
            val intent = Intent(activity, PlayerActivity::class.java)
            intent.putExtra(Track::class.java.simpleName, it)
            startForResult.launch(intent)




        }
    }

    fun render(state:SelectedTrackState){
        when(state){
            is SelectedTrackState.TrackContent -> showSelectedTrack(state.tracks)
            is SelectedTrackState.Error -> showError(state.errorMessage)
        }
    }
    fun showSelectedTrack(tracks:List<Track>){
        adapter.track.clear()
        binding.imageView.visibility = View.GONE
        binding.text.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        adapter.track.addAll(tracks.toMutableList())
        adapter.notifyDataSetChanged()

    }
    fun showError(error:String){
        recyclerView.visibility = View.GONE
        binding.imageView.visibility = View.VISIBLE


        binding.text.text = error
        binding.text.visibility = View.VISIBLE


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_IS_FAVORITE){
            if(requestCode==Activity.RESULT_OK){
                viewModel.getSelectedTrack()
            }
        }
    }

}