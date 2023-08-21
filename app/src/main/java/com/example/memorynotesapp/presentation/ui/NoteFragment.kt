package com.example.memorynotesapp.presentation.ui

import android.app.AlertDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import com.example.core.data.Note
import com.example.memorynotesapp.R
import com.example.memorynotesapp.framework.viewmodel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteFragment : Fragment() {

    private var noteId = 0L
    private val viewModel: NoteViewModel by viewModels()
    private var currentNote = Note("", "", 0L, 0L)

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
        val btnDeleteNote = view.findViewById<FloatingActionButton>(R.id.btnDeleteNote)

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }
        if (noteId != 0L) {
            viewModel.getNoteById(noteId)
        }

        btnDeleteNote.setOnClickListener {
            if (context != null && noteId != 0L){
                AlertDialog.Builder(context)
                    .setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Yes"){dialogInterface , i ->
                        viewModel.deleteNote(currentNote)
                    }
                    .setNegativeButton("Cancel"){dialogInterface , i ->}
                    .create()
                    .show()
            }
        }

        checkButton.setOnClickListener {
            if (contentView.text.toString() != "" || titleView.text.toString() != "") {
                val time: Long = System.currentTimeMillis()
                currentNote.title = titleView.text.toString()
                currentNote.content = contentView.text.toString()
                currentNote.updateTime = time
                if (currentNote.id == 0L) {
                    currentNote.creationTime = time
                }
                viewModel.saveNote(currentNote)
            } else {
                Navigation.findNavController(it).popBackStack()
            }
        }
        observeViewModel(titleView, contentView)

    }

    private fun observeViewModel(editTitle: EditText, contentView: EditText) {
        viewModel.saved.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show()
                hideKeyBoard(editTitle)
                Navigation.findNavController(editTitle).popBackStack()
            } else {
                Toast.makeText(
                    context,
                    "Something went wrong, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.currentNote.observe(viewLifecycleOwner) { note ->
            note?.let {
                currentNote = it
                editTitle.setText(it.title, TextView.BufferType.EDITABLE)
                contentView.setText(it.content, TextView.BufferType.EDITABLE)
            }

        }
    }

    private fun hideKeyBoard(titleView: View) {
        val imm: InputMethodManager =
            context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(titleView.windowToken, 0)
    }
}