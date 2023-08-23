package com.example.playlistmaker.playlist.mediateca.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
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
    var textNoPlayList:TextView? = null
    var imageView:ImageView? = null
    var onClickListener: PlayListAdapter.PlaylistClickListener? =null

     var recyclerView: RecyclerView? = null
    private val viewModel: PlayListsViewModel by viewModel {
        parametersOf()
    }
    private var adapter:PlayListAdapter? = null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlaylistsBinding {
        return FragmentPlaylistsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        textNoPlayList = binding.textNoPlayList
        imageView = binding.imageView
        recyclerView = binding.recyclerViewPlayLists
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = adapter
        viewModel.getStateLiveData().observe(requireActivity()){ render(it)}
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2, )
        binding.btNewPlayList.setOnClickListener {
            val intent = Intent(requireActivity(), PlayListActivity::class.java)
            startActivity(intent)
        }





    }

    override fun onResume() {
        super.onResume()



    }
    private fun render(state:PlayListsScreenState){
        when(state){
            is PlayListsScreenState.showPlayLists -> showPlayLists(state.playLists)
        }
    }
    private fun showPlayLists(playList:List<PlayList>?) {
        if (playList.isNullOrEmpty()) {
            adapter?.playList?.clear()
            adapter?.notifyDataSetChanged()
            recyclerView?.visibility = View.GONE
            textNoPlayList?.visibility = View.VISIBLE
            imageView?.visibility = View.VISIBLE



        }else{
            adapter?.playList?.clear()
            adapter?.playList?.addAll(playList)
            adapter?.notifyDataSetChanged()
            recyclerView?.visibility = View.VISIBLE
            textNoPlayList?.visibility = View.GONE
            imageView?.visibility = View.GONE

        }
    }
    private fun initAdapter(){
        adapter = PlayListAdapter(object: PlayListAdapter.PlaylistClickListener{
            override fun onPlayListClick(playList: PlayList) {
                val bundle = bundleOf("idPlayList" to playList.playListId)
                findNavController().navigate(R.id.action_mediatecaFragment_to_aboutPlayListFragment, bundle)
            }
        })
    }

    companion object {
        fun newInstance() = PlayListsFragment().apply {}
    }
}