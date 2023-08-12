package com.example.playlistmaker.playlist.playlist.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.Glide
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentPlayListBinding
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.playlist.presentation.viewmodel.PlayListViewModel
import com.example.playlistmaker.playlist.playlist.ui.models.createplaylist.CreatePlayListButtonStatus
import com.example.playlistmaker.playlist.playlist.ui.models.createplaylist.PlayListScreenState
import com.example.playlistmaker.playlist.util.BindingFragment
import com.google.android.material.textfield.TextInputLayout

import kotlinx.android.synthetic.main.fragment_play_list.inputEditTextDescription
import kotlinx.android.synthetic.main.fragment_play_list.inputEditTextName
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlayListFragment:BindingFragment<FragmentPlayListBinding>() {

    lateinit var namePlayList:String

    private val viewModel: PlayListViewModel by viewModel{
        parametersOf()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlayListBinding {
        return FragmentPlayListBinding.inflate(inflater, container, false)
    }
    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCreatePlayListButtonStatusLiveData().observe(requireActivity()){ createPlayListButtonStatus(it) }
        viewModel.getPlayListStateLiveData().observe(requireActivity()){render(it)}
        viewModel.showScreen()

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if(uri!=null) {
                    Glide.with(binding.pickImage).load(
                        uri.toString()
                    )
                        .centerCrop()
                        .into(binding.pickImage)
                    viewModel.addImage(uri.toString())
                }
            }

        binding.pickImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.leftArrowPlaylist.setOnClickListener {
            viewModel.openDialog(requireActivity())
        }

        inputEditTextName.editText?.doOnTextChanged {  inputText, _, _, _ ->
            inputEditTextName.inputTextChangeHandler(inputText)
        }

        inputEditTextDescription.editText?.doOnTextChanged{inputText, _, _, _ ->
            inputEditTextDescription.inputTextChangeHandler(inputText)

        }
        binding.inputEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                viewModel.addName(p0.toString())
                namePlayList = p0.toString()
                viewModel.unlockInsertBottom()
            }
        })
        binding.inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.inputEditText.clearFocus()
                true
            } else {
                false
            }
        }

        binding.inputEditTextDescrip.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                viewModel.addDesription(p0.toString())
            }
        })
        binding.inputEditTextDescrip.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyBoard()
                binding.inputEditTextDescrip.clearFocus()
                true
            } else {
                false
            }
        }
        binding.btCreatePlayList.setOnClickListener {
            viewModel.insertPlayList()
            Toast.makeText(requireActivity(), "${resources.getString(R.string.playList)} $namePlayList  ${resources.getString(R.string.created)}", Toast.LENGTH_LONG).show()

        }

    }
    private fun TextInputLayout.inputTextChangeHandler(text:CharSequence?){
        if(text.isNullOrEmpty()) this.setInputStrokeColor(EMPTY_STROKE_COLOR) else this.setInputStrokeColor(
            FILED_STROKE_COLOR
        )
    }

    private fun TextInputLayout.setInputStrokeColor(colorStateList:Int){
        this.defaultHintTextColor = resources.getColorStateList(colorStateList, null)
        this.setBoxStrokeColorStateList(resources.getColorStateList(colorStateList, null))
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                viewModel.openDialog(requireActivity())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            callback
        )
    }

    private fun hideKeyBoard(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.getWindowToken(), 0)
    }

    private fun createPlayListButtonStatus(state: CreatePlayListButtonStatus){
        if(state.clickable!=false){
            changeClickable(true)
        }else{
            changeClickable(false)
        }
    }
    private fun render(state: PlayListScreenState){
        when(state){
            is PlayListScreenState.Finish -> requireActivity().finish()
            is PlayListScreenState.showScreen -> showScreen(state.playList)
        }
    }

    private fun changeClickable(clickable:Boolean){
        when(clickable){
            false -> {binding.btCreatePlayList.isEnabled = false
                binding.btCreatePlayList.setImageResource(R.drawable.button__create_playlist)
            }
            true ->{ binding.btCreatePlayList.isEnabled = true
                binding.btCreatePlayList.setImageResource(R.drawable.button_available)
            }
        }
    }
    private fun showScreen(playList: PlayList){
        binding.pickImage.setImageURI(playList.image?.let { Uri.parse(it) })
        binding.inputEditTextDescrip.setText(playList.description)
        binding.inputEditText.setText(playList.name)
    }
    companion object{
        const val EMPTY_STROKE_COLOR = R.color.empty_stroke_color
        const val FILED_STROKE_COLOR = R.drawable.filed_stroke_color


    }


}