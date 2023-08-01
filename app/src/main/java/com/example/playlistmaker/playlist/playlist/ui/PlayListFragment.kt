package com.example.playlistmaker.playlist.playlist.ui

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.databinding.FragmentPlayListBinding
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.playlist.presentation.PlayListViewModel
import com.example.playlistmaker.playlist.playlist.ui.models.CreatePlayListButtonStatus
import com.example.playlistmaker.playlist.playlist.ui.models.PlayListScreenState
import com.example.playlistmaker.playlist.util.BindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlayListFragment:BindingFragment<FragmentPlayListBinding>() {
    private val viewModel:PlayListViewModel by viewModel{
        parametersOf()
    }


    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlayListBinding {
        return FragmentPlayListBinding.inflate(inflater, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCreatePlayListButtonStatusLiveData().observe(requireActivity()){ createPlayListButtonStatus(it) }
        viewModel.getPlayListStateLiveData().observe(requireActivity()){render(it)}
        viewModel.showScreen()

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                //обрабатываем событие выбора пользователем фотографии
                if (uri != null) {
                    binding.pickImage.setImageURI(uri)
                    viewModel.addImage(uri.toString())
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        binding.pickImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }
        binding.inputEditTextName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                viewModel.addName(p0.toString())
                viewModel.unlockInsertBottom()
            }

        })
        binding.inputEditTextDescription.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                viewModel.addDesription(p0.toString())
            }
        })
        binding.btCreatePlayList.setOnClickListener {

            viewModel.openDialog(requireActivity())
        }
        requireActivity().onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.openDialog(requireActivity())
            }
        })

    }
    private fun createPlayListButtonStatus(state:CreatePlayListButtonStatus){
        if(state.clickable!=false){
            changeClickable(true)
        }else{
            changeClickable(false)
        }
    }
    private fun render(state: PlayListScreenState){
        when(state){
            is PlayListScreenState.Finish -> findNavController().navigateUp()
            is PlayListScreenState.showScreen -> showScreen(state.playList)
        }
    }

    private fun changeClickable(clickable:Boolean){
        when(clickable){
            false -> binding.btCreatePlayList.isEnabled = false
            true -> binding.btCreatePlayList.isEnabled = true
        }
    }
    private fun showScreen(playList: PlayList){
        binding.pickImage.setImageURI(playList.image?.let { Uri.parse(it) })
        binding.inputEditTextDescription.setText(playList.description)
        binding.inputEditTextName.setText(playList.name)
    }


}