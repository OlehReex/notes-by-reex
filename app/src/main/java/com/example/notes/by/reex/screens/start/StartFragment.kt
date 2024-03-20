package com.example.notes.by.reex.screens.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.notes.by.reex.R
import com.example.notes.by.reex.databinding.FragmentStartBinding
import com.example.notes.by.reex.utils.APP_ACTIVITY
import com.example.notes.by.reex.utils.EMAIL
import com.example.notes.by.reex.utils.PASSWORD
import com.example.notes.by.reex.utils.TYPE_FIREBASE
import com.example.notes.by.reex.utils.TYPE_ROOM
import com.example.notes.by.reex.utils.showToast

class StartFragment : Fragment() {
    private var binding: FragmentStartBinding? = null
    private val mBinding get() = binding!!
    private lateinit var viewModel: StartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        viewModel = ViewModelProvider(this).get(StartViewModel::class.java)
        mBinding.buttonRoom.setOnClickListener {
            viewModel.initDatabase(TYPE_ROOM) {
                APP_ACTIVITY.navController.navigate(R.id.action_startFragment_to_mainFragment)
            }
        }

        mBinding.buttonFirebase.setOnClickListener {
            mBinding.inputEmail.visibility = View.VISIBLE
            mBinding.inputPassword.visibility = View.VISIBLE
            mBinding.buttonLogin.visibility = View.VISIBLE
            mBinding.buttonLogin.setOnClickListener {
                val inputEmail = mBinding.inputEmail.text.toString()
                val inputPassword = mBinding.inputPassword.text.toString()
                if(inputEmail.isNotEmpty() && inputPassword.isNotEmpty()) {
                    EMAIL = inputEmail
                    PASSWORD = inputPassword
                    viewModel.initDatabase(TYPE_FIREBASE) {
                        APP_ACTIVITY.navController.navigate(R.id.action_startFragment_to_mainFragment)
                    }
                } else {
                    showToast(getString(R.string.toast_enter_password_and_email))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
