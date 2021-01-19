package com.example.capstone_forum.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstone_forum.Comment
import com.example.capstone_forum.Post
import com.example.capstone_forum.repository.CommentRepository
import com.example.capstone_forum.repository.PostRepository
import com.example.capstone_forum.repository.UserRepository
import kotlinx.coroutines.launch

class CommentViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "FIREBASE"
    private val commentRepository: CommentRepository = CommentRepository()

    val comments: LiveData<List<Comment>> = commentRepository.comments

    private val _errorText: MutableLiveData<String> = MutableLiveData()
    val errorText: LiveData<String> get() = _errorText

    fun createComment(post: Post) {
        viewModelScope.launch {
            try {
                commentRepository.createComment(post)
            } catch (exception: PostRepository.CreatePostError) {
                val errorMessage = "Something went wrong while trying to create a comment"
                Log.e(TAG, exception.message ?: errorMessage)
                _errorText.value = errorMessage
            }
        }
    }

}