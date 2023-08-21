package com.example.memorynotesapp.framework.di

import com.example.memorynotesapp.framework.viewmodel.ListViewModel
import com.example.memorynotesapp.framework.viewmodel.NoteViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCasesModule::class])
interface ViewModelComponent {
    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)
}