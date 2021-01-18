package com.example.capstone_forum.home

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.capstone_forum.Post
import com.example.capstone_forum.R
import com.example.capstone_forum.viewmodel.PostViewModel
import com.example.capstone_forum.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_make_post.*
import kotlinx.android.synthetic.main.item_post_card.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class HomeMakePostFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()
    private val postViewModel: PostViewModel by activityViewModels()

    private var storageReference: StorageReference = FirebaseStorage.getInstance()
        .getReference("post-images/")

    private var username = ""

    private val spinnerList =
        arrayListOf("Soccer_fanatics", "Car_enthusiasts", "Movie_fans", "Computer_nerds")

    private var firebase: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseDB = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_make_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Make a Post"
        setHasOptionsMenu(true)

        initViews()
        initUser()
    }

    private fun initUser() {
        userViewModel.getUser(firebase.currentUser!!.uid)
        userViewModel.user.observe(viewLifecycleOwner) {
            username = it.firstName
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == -1) {
            if(requestCode == 1) {
                val selectedImageUri = data?.data

                val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, selectedImageUri)
//                profileHeader.setImageBitmap(bitmap)

                if(selectedImageUri != null) {
                    saveImageToFirebase(selectedImageUri)
                }
            }
        }
    }

    private fun saveImageToFirebase(uri: Uri) {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()

        val reference = storageReference.child(UUID.randomUUID().toString())
        reference.putFile(uri).addOnSuccessListener {
            reference.downloadUrl.addOnSuccessListener { uriLink ->
//                currentUser.userImage = uriLink.toString()

//                saveUserImage()
                progressDialog.dismiss()
            }
        }
    }

//    private fun saveUserImage() { //TODO: User to post, Import Glide, Current post image update,
//        userViewModel.updateUser(firebaseUser!!.uid, currentUser)
//    }

    private fun pickFromGallery() {
        val intent = Intent()

        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
    }

    private fun initViews() {
        srCategory.adapter =
            ArrayAdapter<String>(requireContext(), R.layout.dropdown_spinner, spinnerList)

        fabMakePost.setOnClickListener {
            if (inputValidation()) {
                makePost()

                Toast.makeText(context, "Post created", Toast.LENGTH_SHORT).show()
            }
        }

        addImageBtn.setOnClickListener {
            pickFromGallery()
        }
    }

    private fun makePost() {
        val intent = Intent(context, HomeActivity::class.java)
        val push = firebaseDB.child("posts").push()

        val newPost = Post(
            push.key!!,
            srCategory.selectedItem.toString(),
            username,
            System.currentTimeMillis(),
            tiTitle.text.toString(),
            tiDescription.text.toString(),
            0,

        )

        postViewModel.createPost(newPost)

        push.setValue(newPost)

        intent.putExtra("newPost", newPost)
        startActivity(intent)
    }

    private fun inputValidation(): Boolean {
        return if (tiTitle.text.toString().isBlank()) {
            tiTitle.error = "Title field cannot be empty"
            false
        } else true
    }

}