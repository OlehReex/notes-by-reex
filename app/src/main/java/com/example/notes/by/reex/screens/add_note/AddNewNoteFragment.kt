package com.example.notes.by.reex.screens.add_note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.notes.by.reex.R
import com.example.notes.by.reex.databinding.FragmentAddNewNoteBinding
import com.example.notes.by.reex.models.AppNote
import com.example.notes.by.reex.utils.APP_ACTIVITY
import com.example.notes.by.reex.utils.showToast

class AddNewNoteFragment : Fragment() {
    private var binding: FragmentAddNewNoteBinding? = null
    private val mBinding get() = binding!!
    private lateinit var viewModel: AddNewNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewNoteBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        viewModel = ViewModelProvider(this).get(AddNewNoteViewModel::class.java)
        mBinding.buttonAddNote.setOnClickListener {
            val noteName = mBinding.inputNoteName.text.toString()
            val noteText = mBinding.inputTextName.text.toString()
                if(noteName.isEmpty()) {
                    showToast(getString(R.string.toast_enter_name))
                }
                else {
                    viewModel.insert(AppNote(name = noteName, text = noteText)) {
                        APP_ACTIVITY.navController.navigate(R.id.action_addNewNoteFragment_to_mainFragment)
                    }
                }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
