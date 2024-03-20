package com.example.notes.by.reex.database

import androidx.lifecycle.LiveData
import com.example.notes.by.reex.models.AppNote

interface DatabaseRepository {
    val allNotes: LiveData<List<AppNote>>

    suspend fun insert(note: AppNote, onSuccess:  () -> Unit)

    suspend fun delete(note: AppNote, onSuccess:  () -> Unit)

    fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {}

    fun onSingOut() {}
}
