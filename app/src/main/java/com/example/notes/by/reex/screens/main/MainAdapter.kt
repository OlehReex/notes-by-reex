package com.example.notes.by.reex.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.by.reex.R
import com.example.notes.by.reex.models.AppNote

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainHolder>() {
    var notesList = emptyList<AppNote>()

    class MainHolder(view: View): RecyclerView.ViewHolder(view) {
        val noteName: TextView = view.findViewById(R.id.item_note_name)
        val noteText: TextView = view.findViewById(R.id.item_note_text)
    }

    override fun onViewAttachedToWindow(holder: MainHolder) {
        holder.itemView.setOnClickListener {
            MainFragment.click(notesList[holder.adapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: MainHolder) {
        holder.itemView.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return MainHolder(view)
    }

    override fun getItemCount(): Int = notesList.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.noteName.text = notesList[position].name
        holder.noteText.text = notesList[position].text
    }

    fun setList(list: List<AppNote>) {
        notesList = list
    }
}