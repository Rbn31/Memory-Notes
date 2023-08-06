package com.example.memorynotesapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.memorynotesapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val add = view.findViewById<FloatingActionButton>(R.id.addNote)

        add.setOnClickListener {
            goToNoteDetails()
        }

    }

    private fun goToNoteDetails(id: Long = 0L) {
        val noteListView = view?.findViewById<RecyclerView>(R.id.noteListView)
        val action: NavDirections = ListFragmentDirections.actionGoToNote(id)

        if (noteListView != null) {
            Navigation.findNavController(noteListView).navigate(action)
        }
    }

}