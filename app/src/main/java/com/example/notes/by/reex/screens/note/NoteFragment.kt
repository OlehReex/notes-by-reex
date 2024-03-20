package com.example.notes.by.reex.screens.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import com.example.notes.by.reex.R
import com.example.notes.by.reex.databinding.FragmentNoteBinding
import com.example.notes.by.reex.models.AppNote
import com.example.notes.by.reex.utils.APP_ACTIVITY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NoteFragment : Fragment() {
    private var binding: FragmentNoteBinding? = null
    private val mBinding get() = binding!!
    private lateinit var viewModel: NoteViewModel
    private lateinit var currentNote: AppNote


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(layoutInflater, container, false)
        currentNote = arguments?.getSerializable("note") as AppNote
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.note_action_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    R.id.button_delete -> {
                        viewModel.delete(currentNote) {
                            GlobalScope.launch(Dispatchers.Main) {
                                APP_ACTIVITY.navController.navigate(R.id.action_noteFragment_to_mainFragment)
                            }
                        }
                    }
                }
                return false
            }
        }, viewLifecycleOwner)

        mBinding.noteName.text = currentNote.name
        mBinding.itemNoteText.text = currentNote.text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
