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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "FIREBASE"
    private val postRepository: PostRepository = PostRepository()
    private val firebase: FirebaseAuth = FirebaseAuth.getInstance()

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

    fun setLiked(liked: Boolean, post: Post) {
        viewModelScope.launch {
            try {
                post.likedOrNot[firebase.currentUser!!.uid] = liked
                postRepository.updatePost(post)
            } catch (exception: PostRepository.SetLikedError) {
                val errorMessage = "Something went wrong while trying to like a post"
                Log.e(TAG, exception.message ?: errorMessage)
                _errorText.value = errorMessage
            }
        }
    }


}