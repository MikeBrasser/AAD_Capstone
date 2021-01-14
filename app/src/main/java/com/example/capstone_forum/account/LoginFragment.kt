package com.example.capstone_forum.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.capstone_forum.R
import com.example.capstone_forum.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var fragmentTransaction: FragmentTransaction

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {

        registerLinkLogin.setOnClickListener {
            loadRegisterPage()
        }

        loginButton.setOnClickListener {
            confirmLogin()
        }

    }

    private fun loadRegisterPage() {
        fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content_activity, RegisterFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun confirmLogin() {
        if (!checkEmailField() || !checkPasswordField()) {
            return
        } else {
            firebaseAuth.signInWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        successfulLogin()
                        Toast.makeText(context, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, signIn.exception!!.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    private fun checkPasswordField(): Boolean {
        return if (passwordInput.text.toString().isBlank()) {
            passwordInput.error = "This field can't be empty!"
            false
        } else {
            true
        }
    }

    private fun checkEmailField(): Boolean {
        return if (emailInput.text.toString().isBlank()) {
            emailInput.error = "This field can't be empty!"
            false
        } else {
            true
        }
    }

    private fun successfulLogin() {
        startActivity(Intent(context, HomeActivity::class.java))
        requireActivity().finish()
    }


}