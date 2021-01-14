package com.example.capstone_forum.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone_forum.account.model.User
import com.google.firebase.database.*
import kotlinx.coroutines.withTimeout

class UserRepository {

    private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> get() = _user

    suspend fun getUser(id: String) {
        try {
            withTimeout(10_000) {
                val query = firebase.getReference("users").child(id)
                query.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val genericTypeIndicator: GenericTypeIndicator<User> =
                            object : GenericTypeIndicator<User>() {}

                        _user.value = snapshot.getValue(genericTypeIndicator)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        println("Error message: ${error.message}")
                    }
                })
            }
        } catch (exception: Exception) {
            throw GetUserError(exception.message.toString(), exception)
        }
    }

    suspend fun setUser(id: String, user: User) {
        try {
            withTimeout(10_000) {
                val query = firebase.getReference("users").child(id)
                query.setValue(user).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        println("Task is successful! ${task.result}")
                    } else {
                        Log.e("FIREBASE", "Error while completing call to database")
                    }
                }
            }
        } catch (exception: Exception) {
            throw SetUserError(exception.message.toString(), exception)
        }
    }

    class GetUserError(message: String, cause: Throwable) : Exception(message, cause) {}
    class SetUserError(message: String, cause: Throwable) : Exception(message, cause) {}
}