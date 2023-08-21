package com.example.memorynotesapp.framework.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.core.data.Note
import com.example.core.repository.NoteRepository
import com.example.memorynotesapp.framework.RoomNoteDataSource
import com.example.memorynotesapp.framework.UseCases
import com.example.memorynotesapp.framework.di.ApplicationModule
import com.example.memorynotesapp.framework.di.DaggerViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel (application: Application): AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val repository = NoteRepository(RoomNoteDataSource(application))

    val saved = MutableLiveData<Boolean>()
    val currentNote = MutableLiveData<Note?>()

    @Inject
    lateinit var useCases: UseCases

    init {
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)
    }

    fun saveNote(note: Note){
        coroutineScope.launch {
            useCases.addNote(note)
            saved.postValue(true)
        }
    }

    fun getNoteById(id: Long){
        coroutineScope.launch {
            val note: Note? = useCases.getNote(id)
            currentNote.postValue(note)
        }
    }

    fun deleteNote(note: Note){
        coroutineScope.launch {
            useCases.removeNote(note)
            saved.postValue(true)
        }
    }
}