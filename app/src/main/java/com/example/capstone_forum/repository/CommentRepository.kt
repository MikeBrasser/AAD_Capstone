package com.example.capstone_forum.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone_forum.Comment
import com.example.capstone_forum.Post
import com.google.firebase.database.*
import kotlinx.coroutines.withTimeout
import java.lang.Exception

class CommentRepository {

    private var firebase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val _comments: MutableLiveData<List<Comment>> = MutableLiveData()
    val comments: LiveData<List<Comment>> get() = _comments

    suspend fun createComment(post: Post) {
        try {
            withTimeout(10_000) {
                val query = firebase.getReference("posts/${post.id}/comments")
                query.setValue(post.comments).addOnFailureListener {
                    println("ERROR: " + it.message.toString())
                }
            }
        } catch (exception: Exception) {
            throw CreateCommentError(exception.message.toString(), exception)
        }
    }

    class CreateCommentError(message: String, cause: Throwable) : Exception(message, cause) {}

}