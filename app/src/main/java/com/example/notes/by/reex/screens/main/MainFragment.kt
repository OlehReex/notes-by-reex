package com.example.notes.by.reex.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.by.reex.R
import com.example.notes.by.reex.databinding.FragmentMainBinding
import com.example.notes.by.reex.models.AppNote
import com.example.notes.by.reex.utils.APP_ACTIVITY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    private val mBinding get() = binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    private lateinit var observerList: Observer<List<AppNote>>
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        progressBar = mBinding.progressBar
        initialization()
        loadData()
    }

    private fun initialization() {
        adapter = MainAdapter()
        recyclerView = mBinding.recyclerView
        recyclerView.adapter = adapter
        observerList = Observer {
            val list = it.asReversed()
            adapter.setList(list)
            adapter.notifyDataSetChanged()
        }
        loadExitMenu()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.allNotes.observe(viewLifecycleOwner, observerList)

        binding?.floatingButtonAddNote?.setOnClickListener {
            APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_addNewNoteFragment)
        }
    }

    private fun loadData() {
        progressBar.visibility = View.VISIBLE
        viewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            if (notes.isNotEmpty()) {
                    progressBar.visibility = View.GONE
            } else {
                mBinding.root.postDelayed({
                    progressBar.visibility = View.GONE
                }, 1000)
            }
        }
    }

    private fun loadExitMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.exit_action_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                GlobalScope.launch(Dispatchers.Main) {
                    viewModel.signOut()
                    APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_startFragment)
                }
                return false
            }
        }, viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        viewModel.allNotes.removeObserver(observerList)
        recyclerView.adapter = null
    }

    companion object {
        fun click(note: AppNote) {
            val bundle = Bundle()
            bundle.putSerializable("note", note)
            APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_noteFragment, bundle)
        }
    }
}
