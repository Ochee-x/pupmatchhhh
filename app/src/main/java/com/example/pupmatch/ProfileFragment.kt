package com.example.finalapp

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupImageClickListeners(view)
        setupNavigationButtons(view)
        loadProfileData(view)
    }

    private fun setupImageClickListeners(view: View) {
        val imageIds = listOf(R.id.image1, R.id.image2, R.id.image3)
        for (id in imageIds) {
            val imageView = view.findViewById<ImageView>(id)
            imageView.setOnClickListener {
                val drawable = imageView.drawable
                showImagePopup(drawable)
            }
        }
    }

    private fun showImagePopup(drawable: Drawable?) {
        val popupImage = ImageView(requireContext()).apply {
            setImageDrawable(drawable)
            adjustViewBounds = true
            scaleType = ImageView.ScaleType.FIT_CENTER
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(popupImage)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun setupNavigationButtons(view: View) {
        val settingsButton = view.findViewById<ImageButton>(R.id.btnSettings)
        val editButton = view.findViewById<Button>(R.id.btnEditProfile)

        settingsButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }

        editButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, EditProfileFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun loadProfileData(view: View) {
        val sharedPref = requireActivity().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

        val name = sharedPref.getString("name", "John Doe")
        val sayings = sharedPref.getString("sayings", "“Live life to the fullest.”")
        val interests = sharedPref.getString("interests", "Dancing, Drawing, Games")

        view.findViewById<TextView>(R.id.textName).text = name
        view.findViewById<TextView>(R.id.textSayings).text = sayings
        view.findViewById<TextView>(R.id.textInterests).text = interests

        val profileImageUri = sharedPref.getString("profileImageUri", null)
        profileImageUri?.let {
            view.findViewById<ImageView>(R.id.imageProfile).setImageURI(Uri.parse(it))
        }

        for (i in 0..2) {
            val uri = sharedPref.getString("featuredImageUri_$i", null)
            if (uri != null) {
                val imageId = when (i) {
                    0 -> R.id.image1
                    1 -> R.id.image2
                    else -> R.id.image3
                }
                view.findViewById<ImageView>(imageId).setImageURI(Uri.parse(uri))
            }
        }
    }
}

