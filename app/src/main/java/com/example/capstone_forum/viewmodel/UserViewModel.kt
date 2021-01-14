package com.example.capstone_forum.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstone_forum.account.model.User
import com.example.capstone_forum.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "FIREBASE"
    private val userRepository: UserRepository = UserRepository()

    val user: LiveData<User> = userRepository.user

    private val _errorText: MutableLiveData<String> = MutableLiveData()

    fun getUser(id: String) {
        viewModelScope.launch {
            try {
                userRepository.getUser(id)
            } catch (exception: UserRepository.GetUserError) {
                val errorMessage = "Something went wrong while trying to get the user"
                Log.e(TAG, exception.message ?: errorMessage)
                _errorText.value = errorMessage
            }
        }
    }

    fun setUser(id: String, user: User) {
        viewModelScope.launch {
            try {
                userRepository.setUser(id, user)
            } catch (exception: UserRepository.SetUserError) {
                val errorMessage = "Something went wrong while setting the user"
                Log.e(TAG, exception.message ?: errorMessage)
                _errorText.value = errorMessage
            }
        }
    }
}