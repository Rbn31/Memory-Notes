package com.example.memorynotesapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.memorynotesapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val checkButton = view.findViewById<FloatingActionButton>(R.id.checkButton)
        checkButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }

}