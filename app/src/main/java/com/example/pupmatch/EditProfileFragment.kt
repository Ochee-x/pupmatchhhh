// EditProfileFragment.kt
package com.example.finalapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class EditProfileFragment : Fragment() {

    private val PICK_PROFILE_IMAGE = 1
    private val PICK_FEATURED_IMAGE_1 = 2
    private val PICK_FEATURED_IMAGE_2 = 3
    private val PICK_FEATURED_IMAGE_3 = 4

    private var profileUri: Uri? = null
    private var featuredUris = arrayOfNulls<Uri>(3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPref = requireActivity().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

        val nameInput = view.findViewById<EditText>(R.id.editName)
        val sayingsInput = view.findViewById<EditText>(R.id.editSayings)
        val interestsInput = view.findViewById<EditText>(R.id.editInterests)
        val btnSave = view.findViewById<Button>(R.id.btnSaveProfile)

        val profileImage = view.findViewById<ImageView>(R.id.editProfileImage)
        val featured1 = view.findViewById<ImageView>(R.id.editFeatured1)
        val featured2 = view.findViewById<ImageView>(R.id.editFeatured2)
        val featured3 = view.findViewById<ImageView>(R.id.editFeatured3)

        nameInput.setText(sharedPref.getString("name", ""))
        sayingsInput.setText(sharedPref.getString("sayings", ""))
        interestsInput.setText(sharedPref.getString("interests", ""))

        profileImage.setOnClickListener { pickImage(PICK_PROFILE_IMAGE) }
        featured1.setOnClickListener { pickImage(PICK_FEATURED_IMAGE_1) }
        featured2.setOnClickListener { pickImage(PICK_FEATURED_IMAGE_2) }
        featured3.setOnClickListener { pickImage(PICK_FEATURED_IMAGE_3) }

        btnSave.setOnClickListener {
            with(sharedPref.edit()) {
                putString("name", nameInput.text.toString())
                putString("sayings", sayingsInput.text.toString())
                putString("interests", interestsInput.text.toString())
                profileUri?.let { putString("profileImageUri", it.toString()) }
                for (i in featuredUris.indices) {
                    featuredUris[i]?.let { putString("featuredImageUri_$i", it.toString()) }
                }
                apply()
            }

            Toast.makeText(requireContext(), "Profile updated!", Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, ProfileFragment())
                .commit()
        }
    }

    private fun pickImage(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            when (requestCode) {
                PICK_PROFILE_IMAGE -> {
                    profileUri = uri
                    view?.findViewById<ImageView>(R.id.editProfileImage)?.setImageURI(uri)
                }
                PICK_FEATURED_IMAGE_1 -> {
                    featuredUris[0] = uri
                    view?.findViewById<ImageView>(R.id.editFeatured1)?.setImageURI(uri)
                }
                PICK_FEATURED_IMAGE_2 -> {
                    featuredUris[1] = uri
                    view?.findViewById<ImageView>(R.id.editFeatured2)?.setImageURI(uri)
                }
                PICK_FEATURED_IMAGE_3 -> {
                    featuredUris[2] = uri
                    view?.findViewById<ImageView>(R.id.editFeatured3)?.setImageURI(uri)
                }
            }
        }
    }
}