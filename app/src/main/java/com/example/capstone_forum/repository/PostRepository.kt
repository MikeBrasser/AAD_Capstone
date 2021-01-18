package com.example.capstone_forum.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone_forum.Post
import com.google.firebase.database.*
import kotlinx.coroutines.withTimeout
import java.lang.Exception

class PostRepository {

    private var firebase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val _post: MutableLiveData<Post> = MutableLiveData()
    val post: LiveData<Post> get() = _post

    private val _posts: MutableLiveData<List<Post>> = MutableLiveData()
    val posts: LiveData<List<Post>> get() = _posts

    suspend fun createPost(post: Post) {
        try {
            withTimeout(10_000) {
                val query = firebase.getReference("posts/${post.id}")
                query.setValue(post).addOnFailureListener {
                    println("ERROR: " + it.message.toString())
                }
            }
        } catch (exception: Exception) {
            throw CreatePostError(exception.message.toString(), exception)
        }
    }

    suspend fun getAllPosts() {
        try {
            withTimeout(10_000) {
                val query = firebase.getReference("posts")
                query.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val genericTypeIndicator: GenericTypeIndicator<HashMap<String, Post>> =
                            object : GenericTypeIndicator<HashMap<String, Post>>() {}

                        val posts = snapshot.getValue(genericTypeIndicator)!!

                        _posts.value = posts.values.toMutableList()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        println("ERROR: ${error.message}")
                    }
                })
            }
        } catch (exception: Exception) {
            throw GetAllPostsError(exception.message.toString(), exception)
        }
    }

    suspend fun getPost(postID: String) {
        try {
            withTimeout(10_000) {
                val query = firebase.getReference("posts/$postID")
                query.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val genericTypeIndicator: GenericTypeIndicator<Post> =
                            object : GenericTypeIndicator<Post>() {}

                        _post.value = snapshot.getValue(genericTypeIndicator)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        println("ERROR: ${error.message}")
                    }
                })
            }
        } catch (exception: Exception) {
            throw GetPostError(exception.message.toString(), exception)
        }
    }

    class CreatePostError(message: String, cause: Throwable) : Exception(message, cause) {}
    class GetAllPostsError(message: String, cause: Throwable) : Exception(message, cause) {}
    class GetPostError(message: String, cause: Throwable) : Exception(message, cause) {}

}