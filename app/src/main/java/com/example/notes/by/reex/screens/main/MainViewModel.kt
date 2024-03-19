package com.example.notes.by.reex.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.notes.by.reex.utils.REPOSITORY

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val allNotes = REPOSITORY.allNotes
}