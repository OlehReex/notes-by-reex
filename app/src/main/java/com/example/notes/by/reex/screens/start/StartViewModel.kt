package com.example.notes.by.reex.screens.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.notes.by.reex.database.firebase.AppFirebaseRepository
import com.example.notes.by.reex.database.room.AppRoomDatabase
import com.example.notes.by.reex.database.room.AppRoomRepository
import com.example.notes.by.reex.utils.REPOSITORY
import com.example.notes.by.reex.utils.TYPE_FIREBASE
import com.example.notes.by.reex.utils.TYPE_ROOM
import com.example.notes.by.reex.utils.showToast

class StartViewModel(application: Application) : AndroidViewModel(application) {
    private val mContext = application

    fun initDatabase(type: String, onSuccess: () -> Unit) {
        when(type) {
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(mContext).getAppRoomDao()
                REPOSITORY = AppRoomRepository(dao)
                onSuccess()
            }
            TYPE_FIREBASE -> {
                REPOSITORY = AppFirebaseRepository()
                REPOSITORY.connectToDatabase({onSuccess()}, {showToast(it)})
            }
        }
    }
}
