package com.example.playlistmaker.playlist.playlist.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.playlist.presentation.viewmodel.PlayListRedactorViewModel
import com.example.playlistmaker.playlist.playlist.presentation.viewmodel.PlayListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditPlayListFragment():PlayListFragment() {

    override val viewModel by viewModel<PlayListRedactorViewModel>()
     var playList:PlayList? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playList = arguments?.getParcelable<PlayList?>("playList")
        Log.d("myLog", "$playList")
        back()
        showEditPlayList()
        redactor()
        btCreatePlayList()
    }

    override fun back() {
        super.back()
        binding.leftArrowPlaylist.setOnClickListener{
            findNavController().navigateUp()
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    override fun btCreatePlayList() {
        super.btCreatePlayList()
        binding.btCreatePlayList.setOnClickListener{
            viewModel.update(playList!!)
            findNavController().navigateUp()
        }

    }
    private fun showEditPlayList(){
        binding.bottomText.text = "Редактировать"
        binding.textView.text = "Редактировать плейлист"
        Glide.with(binding.pickImage).load(
            playList?.image
        )
            .placeholder(R.drawable.add_photo)
            .centerCrop()
            .into(binding.pickImage)
        binding.inputEditText.setText(playList?.name)
        binding.inputEditTextDescrip.setText(playList?.description)
    }
    private fun redactor(){
        binding.inputEditText.doOnTextChanged{inputText, _, _, _ ->
            viewModel.addName(inputText.toString())

        }
        binding.inputEditTextDescrip.doOnTextChanged{inputText, _, _, _ ->
            viewModel.addDesription(inputText.toString())
        }
    }
}