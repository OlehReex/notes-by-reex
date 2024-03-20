package com.example.notes.by.reex.database.room

import androidx.lifecycle.LiveData
import com.example.notes.by.reex.database.DatabaseRepository
import com.example.notes.by.reex.models.AppNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRoomRepository(private val appRoomDao: AppRoomDao): DatabaseRepository {

    override val allNotes: LiveData<List<AppNote>>
        get() = appRoomDao.getAllNotes()

    override suspend fun insert(note: AppNote, onSuccess: () -> Unit) {
        appRoomDao.insert(note)
        withContext(Dispatchers.Main) {
            onSuccess()
        }
    }

    override suspend fun delete(note: AppNote, onSuccess: () -> Unit) {
        appRoomDao.delete(note)
        onSuccess()
    }

    override fun onSingOut() {
        super.onSingOut()
    }
}
