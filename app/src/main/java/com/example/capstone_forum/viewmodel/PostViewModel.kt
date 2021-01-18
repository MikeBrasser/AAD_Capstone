package com.example.capstone_forum.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstone_forum.Post
import com.example.capstone_forum.repository.PostRepository
import com.example.capstone_forum.repository.UserRepository
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "FIREBASE"
    private val postRepository: PostRepository = PostRepository()

    val post: LiveData<Post> = postRepository.post
    val posts: LiveData<List<Post>> = postRepository.posts

    private val _errorText: MutableLiveData<String> = MutableLiveData()
    val errorText: LiveData<String> get() = _errorText

    fun createPost(post: Post) {
        viewModelScope.launch {
            try {
                postRepository.createPost(post)
            } catch (exception: PostRepository.CreatePostError) {
                val errorMessage = "Something went wrong while trying to create a post"
                Log.e(TAG, exception.message ?: errorMessage)
                _errorText.value = errorMessage
            }
        }
    }

    fun getAllPosts() {
        viewModelScope.launch {
            try {
                postRepository.getAllPosts()
            } catch (exception: UserRepository.GetUserError) {
                val errorMessage = "Something went wrong while trying to get all posts"
                Log.e(TAG, exception.message ?: errorMessage)
                _errorText.value = errorMessage
            }
        }
    }

    fun getPost(postID: String) {
        viewModelScope.launch {
            try {
                postRepository.getPost(postID)
            } catch (exception: PostRepository.GetPostError){
                val errorMessage = "Something went wrong while trying to get a post"
                Log.e(TAG, exception.message ?: errorMessage)
                _errorText.value = errorMessage
            }
        }
    }


}