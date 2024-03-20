package com.example.notes.by.reex.database.firebase

import androidx.lifecycle.LiveData
import com.example.notes.by.reex.database.DatabaseRepository
import com.example.notes.by.reex.models.AppNote
import com.example.notes.by.reex.utils.AUTH
import com.example.notes.by.reex.utils.CURRENT_ID
import com.example.notes.by.reex.utils.EMAIL
import com.example.notes.by.reex.utils.NOTE_ID_FIREBASE
import com.example.notes.by.reex.utils.NAME
import com.example.notes.by.reex.utils.PASSWORD
import com.example.notes.by.reex.utils.REF_DATABASE
import com.example.notes.by.reex.utils.TEXT
import com.example.notes.by.reex.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AppFirebaseRepository : DatabaseRepository {

    init {
        AUTH = FirebaseAuth.getInstance()
    }

    override val allNotes: LiveData<List<AppNote>> = AllNotesLiveData()

    override suspend fun insert(note: AppNote, onSuccess: () -> Unit) {
        val noteId = REF_DATABASE.push().key.toString()
        val noteMap = hashMapOf<String, Any>()
        noteMap[NOTE_ID_FIREBASE] = noteId
        noteMap[NAME] = note.name
        noteMap[TEXT] = note.text

        REF_DATABASE.child(noteId)
            .updateChildren(noteMap)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{
                showToast(it.message.toString())
            }
    }

    override suspend fun delete(note: AppNote, onSuccess: () -> Unit) {
        REF_DATABASE.child(note.idFirebase).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{
                showToast(it.message.toString())
            }
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        AUTH.signInWithEmailAndPassword(EMAIL, PASSWORD)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                AUTH.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener {
                        onFail(it.message.toString())
                    }
            }

        CURRENT_ID = AUTH.currentUser?.uid.toString()
        REF_DATABASE = FirebaseDatabase.getInstance().reference
            .child(CURRENT_ID)
    }

    override fun onSingOut() {
        AUTH.signOut()
    }
}
