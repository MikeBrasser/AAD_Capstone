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

    suspend fun createComment(comment: Comment, post: Post) {
        try {
            withTimeout(10_000) {
                val query = firebase.getReference("posts/${post.id}/${comment.id}")
                query.setValue(comment).addOnFailureListener {
                    println("ERROR: " + it.message.toString())
                }
            }
        } catch (exception: Exception) {
            throw CreateCommentError(exception.message.toString(), exception)
        }
    }

    suspend fun getAllComments(post: Post) {
        try {
            withTimeout(10_000) {
                val query = firebase.getReference("posts/${post.id}")
                query.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val genericTypeIndicator: GenericTypeIndicator<HashMap<String, HashMap<String, Comment>>> =
                            object : GenericTypeIndicator<HashMap<String, HashMap<String, Comment>>>() {}

                        val comments = snapshot.getValue(genericTypeIndicator)!!

                        //TODO: FIX THIS
//                        _comments.value = comments.values
                    }

                    override fun onCancelled(error: DatabaseError) {
                        println("ERROR: ${error.message}")
                    }
                })
            }
        } catch (exception: Exception) {
            throw GetAllCommentsError(exception.message.toString(), exception)
        }
    }

    class CreateCommentError(message: String, cause: Throwable) : Exception(message, cause) {}
    class GetAllCommentsError(message: String, cause: Throwable) : Exception(message, cause) {}

}