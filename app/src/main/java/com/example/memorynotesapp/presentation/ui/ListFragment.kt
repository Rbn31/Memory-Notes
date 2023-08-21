package com.example.memorynotesapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.memorynotesapp.R
import com.example.memorynotesapp.framework.viewmodel.ListViewModel
import com.example.memorynotesapp.presentation.adapter.NoteListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment(), ListAction {

    private val viewModel: ListViewModel by viewModels()
    private val noteListAdapter = NoteListAdapter(arrayListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val add = view.findViewById<FloatingActionButton>(R.id.addNote)
        val notesListView = view.findViewById<RecyclerView>(R.id.noteListView)

        notesListView?.adapter = noteListAdapter

        observerViewModel()
        add?.setOnClickListener {
            goToNoteDetails()
        }
    }

    private fun observerViewModel(){
        val notesListView = view?.findViewById<RecyclerView>(R.id.noteListView)
        val loadingView = view?.findViewById<ProgressBar>(R.id.loadingView)

        viewModel.notes.observe(viewLifecycleOwner) { notesList ->
            loadingView?.visibility = View.GONE
            notesListView?.visibility = View.VISIBLE
            noteListAdapter.updateNotes(notesList.sortedByDescending { it.updateTime })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    private fun goToNoteDetails(id: Long = 0L) {
        val noteListView = view?.findViewById<RecyclerView>(R.id.noteListView)
        val action: NavDirections = ListFragmentDirections.actionGoToNote(id)

        if (noteListView != null) {
            Navigation.findNavController(noteListView).navigate(action)
        }
    }

    override fun onClick(id: Long) {
        goToNoteDetails(id)
    }

}