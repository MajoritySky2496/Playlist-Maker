package com.example.playlistmaker.playlist.search.ui.tracks.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.search.presentation.TracksSearchViewModel
import com.example.playlistmaker.playlist.search.ui.tracks.TrackAdapter
import com.example.playlistmaker.playlist.search.ui.tracks.models.TrackSearchState
import com.example.playlistmaker.playlist.util.BindingFragment
import com.example.playlistmaker.playlist.util.NavigationRouter
import com.google.android.material.internal.ViewUtils
import kotlinx.android.synthetic.main.fragment_search.progressBar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchFragment:BindingFragment<FragmentSearchBinding>() {
    private lateinit var inputEditText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var placeHolderMessage: TextView
    private lateinit var placeHolderNoConnection: ImageView
    private lateinit var placeHolderNothingFound: ImageView
    private lateinit var refreshButton: ImageView
    lateinit var removeButton: ImageView
    lateinit var history: TextView
    lateinit var noConnectionLayout: FrameLayout
    lateinit var clearButton: ImageView
    lateinit var backButton: ImageView
    lateinit var trackHistoryLinear: LinearLayout
    private val adapter = TrackAdapter {
    }
    val viewModel: TracksSearchViewModel by  viewModel{
        parametersOf()
    }
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         inputEditText = binding.inputEditText
         recyclerView = binding.recyclerViewHistory
         placeHolderMessage = binding.placeholderMessage
         placeHolderNoConnection = binding.placehoderNoConnection
         placeHolderNothingFound= binding.placeholderNothingFound
         refreshButton = binding.removeButton
         removeButton= binding.removeButton
         history = binding.history
         noConnectionLayout = binding.noConnectionLayout
         clearButton= binding.clearIcon
         backButton= binding.backButton
         trackHistoryLinear = binding.trackHistory
        viewModel.observeState().observe(requireActivity()){render(it)     }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        listener()
        editTextRequestFocus()

    }
    private fun showTrackList(track: List<Track>){
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        adapter.track = track.toMutableList()
        history.visibility = View.GONE
        removeButton.visibility = View.GONE
        hideKeyBoard()
        adapter.notifyDataSetChanged()
    }
    private fun showHistory(historyTrack:List<Track>) {
        if (inputEditText.text.isEmpty() && historyTrack.isNotEmpty() && inputEditText.hasFocus()) {
            recyclerView.visibility = View.VISIBLE
            history.visibility = View.VISIBLE
            removeButton.visibility = View.VISIBLE
            adapter.track.clear()
            adapter.track.addAll(historyTrack.toMutableList())
            adapter.notifyDataSetChanged()
        }
    }
    private fun showError(errorMessage: String){
        placeHolderMessage.text = errorMessage
        placeHolderNothingFound.visibility = View.INVISIBLE
        noConnectionLayout.visibility = View.VISIBLE
        placeHolderMessage.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        history.visibility = View.GONE
        removeButton.visibility = View.GONE
        progressBar.visibility = View.GONE
    }
    private fun showEmpty(emptyMessage:String){
        placeHolderMessage.text = emptyMessage
        placeHolderNothingFound.visibility = View.VISIBLE
        noConnectionLayout.visibility = View.GONE
        placeHolderMessage.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        history.visibility = View.GONE
        removeButton.visibility = View.GONE
    }
    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
    private fun removeHistory(){
        viewModel.removeTrackHistory()
        recyclerView.visibility = View.GONE
        history.visibility = View.GONE
        removeButton.visibility = View.GONE
        adapter.notifyDataSetChanged()
    }
    private fun refresh(s: CharSequence?){
        viewModel.refreshSearchTrack(s?.toString() ?: "")
        placeHolderMessage.visibility = View.GONE
        noConnectionLayout.visibility = View.GONE
        placeHolderNothingFound.visibility = View.INVISIBLE
    }
    private fun editTextRequestFocus(){
        inputEditText.postDelayed({ inputEditText.requestFocus() }, 500)
    }
    @SuppressLint("RestrictedApi")
    private fun hideKeyBoard(){
        ViewUtils.hideKeyboard(requireActivity().currentFocus ?: View(requireContext()))
    }
    private fun showLoading(){
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        history.visibility = View.GONE
        removeButton.visibility = View.GONE
    }
    fun render(state: TrackSearchState){
        when(state){
            is TrackSearchState.Loading -> showLoading()
            is TrackSearchState.TrackContent -> showTrackList(state.tracks)
            is TrackSearchState.HistroryContent -> showHistory(state.historyTrack)
            is TrackSearchState.Error-> showError(state.errorMessage)
            is TrackSearchState.Empty-> showEmpty(state.message)
        }
    }
    private fun clearInputEditText(){
        inputEditText.setText("")
        viewModel.clearInputEditText()
        editTextRequestFocus()
        progressBar.visibility = View.GONE
        placeHolderMessage.visibility = View.GONE
        noConnectionLayout.visibility = View.GONE
        placeHolderNothingFound.visibility = View.INVISIBLE
        hideKeyBoard()
        adapter.notifyDataSetChanged()
    }
    @SuppressLint("RestrictedApi", "CommitPrefEdits")
    private fun listener() {

        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            viewModel.setOnFocus(inputEditText.text.toString(), hasFocus)
        }
        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButton.visibility = clearButtonVisibility(s)
                viewModel.onSearchTextChanged(changedText = s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {
                placeHolderNothingFound.visibility = View.GONE
                placeHolderMessage.visibility = View.GONE
                noConnectionLayout.visibility = View.GONE
            }

        })


        refreshButton.setOnClickListener {
            refresh(inputEditText.text)
        }
        removeButton.setOnClickListener {
            removeHistory()
        }
        clearButton.setOnClickListener {
            clearInputEditText()
        }

        adapter.onItemClick = {
            viewModel.trackAddInHistoryList(it)
            viewModel.loadTrackList(inputEditText.text.toString())
            adapter.notifyDataSetChanged()
            NavigationRouter().openActivity(it, requireActivity())
        }
    }


}