package com.example.notes.by.reex.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.by.reex.R
import com.example.notes.by.reex.databinding.FragmentMainBinding
import com.example.notes.by.reex.models.AppNote
import com.example.notes.by.reex.utils.APP_ACTIVITY

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
        }
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
