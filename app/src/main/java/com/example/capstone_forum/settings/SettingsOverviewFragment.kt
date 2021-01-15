package com.example.capstone_forum.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.capstone_forum.R
import com.example.capstone_forum.account.AccountActivity
import com.example.capstone_forum.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings_overview.*

class SettingsOverviewFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()

    private var firebase: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Settings"

        initViews()
    }

    private fun initViews() {
        userViewModel.getUser(firebase.currentUser!!.uid)

        userViewModel.user.observe(viewLifecycleOwner) {
            fullName.text = getString(R.string.fullNameString, it.firstName, it.lastName)
            fullName.isVisible = true
        }

        logOutBtn.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        firebase.signOut()
        startActivity(Intent(context, AccountActivity::class.java))
    }
}