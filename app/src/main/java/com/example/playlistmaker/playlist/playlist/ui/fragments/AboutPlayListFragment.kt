package com.example.playlistmaker.playlist.playlist.ui.fragments

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentAboutPlaylistBinding
import com.example.playlistmaker.playlist.player.ui.PlayerActivity
import com.example.playlistmaker.playlist.player.ui.models.ToastScreenState
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.playlist.presentation.viewmodel.AboutPlayListViewModel
import com.example.playlistmaker.playlist.playlist.ui.models.aboutplaylist.AboutPlayListState
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.search.ui.tracks.TrackAdapter
import com.example.playlistmaker.playlist.util.BindingFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlin.time.Duration
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

class AboutPlayListFragment : BindingFragment<FragmentAboutPlaylistBinding>() {
    lateinit var recyclerView: RecyclerView
    lateinit var playListBottomSheetBehavior: View
    lateinit var playListEditBottomSheetBehavior: View
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    lateinit var editBottomSheetBehavior: BottomSheetBehavior<View>
    var playListCopy: PlayList? = null
    var trackList: MutableList<Track> = mutableListOf()
    var idPlayList: Int? = null
    private val adapter = TrackAdapter {
    }
    private val viewModel: AboutPlayListViewModel by viewModel {
        parametersOf()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAboutPlaylistBinding {
        return FragmentAboutPlaylistBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idPlayList = arguments?.getInt("idPlayList")
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data

                }
            }

        viewModel.getAboutPlayListStateLiveData().observe(requireActivity()) { render(it) }
        viewModel.getToastScreenState().observe(requireActivity()) { toastState(it) }
        viewModel.getPlayList(idPlayList)
        recyclerView = binding.recyclerViewTracks
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        playListBottomSheetBehavior = binding.playlistBottomSheet
        playListEditBottomSheetBehavior = binding.editPlaylistBottomSheet

        bottomSheetBehavior = BottomSheetBehavior.from(playListBottomSheetBehavior).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }
        editBottomSheetBehavior = BottomSheetBehavior.from(playListEditBottomSheetBehavior).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }
        binding.menu.setOnClickListener {
            editBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        editBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.overlay.visibility = View.GONE
                    }

                    else -> {
                        binding.overlay.visibility = View.VISIBLE
                    }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        binding.leftArrowPlaylist.setOnClickListener {
            findNavController().navigateUp()
        }
        adapter.onItemClick = {
            val intent = Intent(activity, PlayerActivity::class.java)
            intent.putExtra(Track::class.java.simpleName, it)
            startForResult.launch(intent)
        }
        adapter.onItemLongClick = {
            Log.d("longClick", "$it")
            viewModel.openDialog(requireContext(), it!!)
        }
        binding.editInf.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("playList", playListCopy)
            val fragment = AboutPlayListFragment()
            fragment.arguments = bundle

            findNavController().navigate(
                R.id.action_aboutPlayListFragment_to_editPlayListFragment,
                bundle
            )
        }
        binding.share.setOnClickListener {
            clickShare()
        }
        binding.sharePlayList.setOnClickListener {
            clickShare()
        }
        clickDelete()
    }
    fun clickShare() {
        viewModel.shareClick(playListCopy!!, trackList)
    }
    fun clickDelete() {
        binding.deletePlaylist.setOnClickListener {
            viewModel.deletePlayList(playListCopy?.playListId!!)

        }
    }

    fun toastState(state: ToastScreenState) {
        when (state) {
            is ToastScreenState.toastText -> showToast(state.text)
            is ToastScreenState.showToast -> {}
        }
    }

    fun showToast(message: String) {
        Toast.makeText(requireActivity(), "$message", Toast.LENGTH_LONG).show()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getPlayList(idPlayList)
    }

    private fun render(state: AboutPlayListState) {
        when (state) {
            is AboutPlayListState.ShowInfOfPlayList -> showScreen(
                state.playList,
                state.track,
                state.trackDuration
            )

            is AboutPlayListState.GoBack -> goBack()
        }
    }

    fun goBack() {
        findNavController().navigateUp()
    }

    private fun showScreen(playList: PlayList, tracks: MutableList<Track>, trackDuration: String) {
        playListCopy = playList
        trackList.addAll(tracks)
        binding.playListName.text = playList.name
        binding.playerPlayLists.text = playList.name
        binding.playerNumberOfTracks.text = playList.numberTracks
        binding.description.text = playList.description
        binding.description.visibility =
            if (playList.description!!.isNotEmpty()) View.VISIBLE else View.GONE
        binding.numberTracks.text = playList.numberTracks
        binding.tracksDuration.text = trackDuration
        glide(binding.playListImage, playList.image)
        glide(binding.yourImage, playList.image)

        if (tracks.isNotEmpty()) {
            adapter.track.clear()
            adapter.track.addAll(tracks)
            adapter.notifyDataSetChanged()
            binding.ic1.visibility = View.VISIBLE
            binding.playListEmpty.visibility = View.GONE
        } else {
            adapter.track.clear()
            adapter.notifyDataSetChanged()
            binding.ic1.visibility = View.VISIBLE
            binding.playListEmpty.visibility = View.VISIBLE
        }
    }

    private fun glide(view: ImageView, image: String?) {
        Glide.with(view).load(
            image
        )
            .placeholder(R.drawable.ic_placeholder_mediateca)
            .apply(
                (RequestOptions.bitmapTransform(
                    RoundedCorners(
                        binding.playListImage.resources.getDimensionPixelSize(
                            R.dimen.radius_image_track
                        )
                    )
                ))
            ).into(view)
    }
}