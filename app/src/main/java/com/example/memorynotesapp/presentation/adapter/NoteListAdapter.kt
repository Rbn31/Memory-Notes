package com.example.memorynotesapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.Note
import com.example.memorynotesapp.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.Date

class NoteListAdapter(var noteList: ArrayList<Note>) : RecyclerView.Adapter<NoteListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteListViewHolder {
        return NoteListViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    fun updateNotes(newNotes: List<Note>){
        noteList.clear()
        noteList.addAll(newNotes)
        notifyDataSetChanged()
    }

}

class NoteListViewHolder(val item: ItemNoteBinding) : RecyclerView.ViewHolder(item.root) {
    fun bind(data: Note) {
        item.apply {
            title.text = data.title
            content.text = data.content

            val dateFormated = SimpleDateFormat("MMM dd, HH:mm:ss")
            val resultDate = Date(data.updateTime)
            date.text = "Last updated: ${dateFormated.format(resultDate)}"
        }

    }
}