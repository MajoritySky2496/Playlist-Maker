package com.example.playlistmaker.playlist.mediateca.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.FragmentPlaylistsBinding
import com.example.playlistmaker.playlist.mediateca.presentation.PlayListsViewModel
import com.example.playlistmaker.playlist.mediateca.presentation.model.PlayListsScreenState
import com.example.playlistmaker.playlist.mediateca.ui.adapter.PlayListAdapter
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.playlist.ui.PlayListActivity
import com.example.playlistmaker.playlist.util.BindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlayListsFragment : BindingFragment<FragmentPlaylistsBinding>() {
    lateinit var recyclerView: RecyclerView
    private val viewModel: PlayListsViewModel by viewModel {
        parametersOf()
    }
    private val adapter = PlayListAdapter()


    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlaylistsBinding {
        return FragmentPlaylistsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerViewPlayLists
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        viewModel.getStateLiveData().observe(requireActivity()){ render(it)}
        viewModel.showPlayList()
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, )
        binding.btNewPlayList.setOnClickListener {
            val intent = Intent(requireActivity(), PlayListActivity::class.java)
            startActivity(intent)
        }

    }



    override fun onResume() {
        super.onResume()
        viewModel.showPlayList()
    }
    private fun render(state:PlayListsScreenState){
        when(state){
            is PlayListsScreenState.showPlayLists -> showPlayLists(state.playLists)
        }
    }
    private fun showPlayLists(playList:List<PlayList>){

        adapter.playList.clear()
        adapter.playList.addAll(playList)
        adapter.notifyDataSetChanged()
        recyclerView.visibility = View.VISIBLE
        binding.textNoPlayList.visibility = View.GONE
        binding.imageView.visibility = View.GONE


    }

    companion object {
        fun newInstance() = PlayListsFragment().apply {}
    }
}