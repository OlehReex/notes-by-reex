package com.example.notes.by.reex.database.firebase

import androidx.lifecycle.LiveData
import com.example.notes.by.reex.models.AppNote
import com.example.notes.by.reex.utils.REF_DATABASE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AllNotesLiveData : LiveData<List<AppNote>>() {
    private val listener = object : ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            value = snapshot.children.map {
                it.getValue(AppNote::class.java)?: AppNote()
            }
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    override fun onActive() {
        REF_DATABASE.addValueEventListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        REF_DATABASE.removeEventListener(listener)
        super.onInactive()
    }
}
