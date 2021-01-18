package com.example.capstone_forum.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.capstone_forum.R
import com.example.capstone_forum.account.model.User
import com.example.capstone_forum.home.HomeActivity
import com.example.capstone_forum.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val userViewModel: UserViewModel by activityViewModels()

    private lateinit var firstName: String
    private lateinit var lastName: String

    private lateinit var firstNameInput: TextInputEditText
    private lateinit var lastNameInput: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }

    private fun initViews(view: View) {
        firstNameInput = view.findViewById(R.id.firstNameInputRegister)
        lastNameInput = view.findViewById(R.id.lastNameInputRegister)

        loginRedirect.setOnClickListener {
            toLoginPage()
        }

        registerButton.setOnClickListener {
            confirmRegister()
        }
    }

    private fun confirmRegister() {
        if (!checkFirstNameField() || !checkLastNameField() || !checkEmailField() || !checkPasswordField()) {
            return
        } else {
            firebaseAuth.createUserWithEmailAndPassword(
                emailInputRegister.text.toString(),
                passwordInputRegister.text.toString()
            ).addOnCompleteListener { register ->
                if (register.isSuccessful) {
                    userViewModel.setUser(firebaseAuth.currentUser!!.uid, User(firstName, lastName))
                    toHome()
                } else {
                    Toast.makeText(context, register.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkFirstNameField(): Boolean {
        firstName = firstNameInput.text.toString()

        return if (firstName.isBlank()) {
            firstNameInputRegister.error = "This field can't be empty!"
            false
        } else {
            true
        }
    }

    private fun checkLastNameField(): Boolean {
        lastName = lastNameInput.text.toString()

        return if (lastName.isBlank()) {
            lastNameInputRegister.error = "This field can't be empty!"
            false
        } else {
            true
        }
    }

    private fun checkEmailField(): Boolean {
        return if (emailInputRegister.text.toString().isBlank()) {
            emailInputRegister.error = "This field can't be empty!"
            false
        } else {
            true
        }
    }

    private fun checkPasswordField(): Boolean {
        return if (passwordInputRegister.text.toString().isBlank()) {
            passwordInputRegister.error = "This field can't be empty!"
            false
        } else {
            true
        }
    }

    private fun toLoginPage() {
        parentFragmentManager.popBackStack()
    }

    private fun toHome() {
        startActivity(Intent(context, HomeActivity::class.java))
        requireActivity().finish()
    }
}