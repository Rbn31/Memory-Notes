package com.example.memorynotesapp.presentation.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.core.data.Note
import com.example.memorynotesapp.R
import com.example.memorynotesapp.framework.viewmodel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteFragment : Fragment() {

    private val viewModel: NoteViewModel by viewModels()

    private var currentNote = Note("", "", 0L,0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val checkButton = view.findViewById<FloatingActionButton>(R.id.checkButton)
        val contentView = view.findViewById<EditText>(R.id.contentView)
        val titleView = view.findViewById<EditText>(R.id.titleView)

        checkButton.setOnClickListener {
            if (contentView.text.toString() != "" || titleView.text.toString() != ""){
                val time: Long = System.currentTimeMillis()
                currentNote.title = titleView.text.toString()
                currentNote.content = contentView.text.toString()
                currentNote.updateTime = time
                if (currentNote.id == 0L){
                    currentNote.creationTime = time
                }
                viewModel.saveNote(currentNote)
            } else {
                Navigation.findNavController(it).popBackStack()
            }
        }
        observeViewModel(titleView)

    }

    private fun observeViewModel(titleView: View){
        viewModel.saved.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show()
                hideKeyBoard(titleView)
                Navigation.findNavController(titleView).popBackStack()
            }else{
                Toast.makeText(context, "Something went wrong, please try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideKeyBoard(titleView: View){
        val imm: InputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(titleView.windowToken, 0)
    }
}