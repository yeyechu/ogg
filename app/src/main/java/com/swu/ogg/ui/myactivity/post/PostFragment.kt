package com.swu.ogg.ui.myactivity.post

import android.app.Dialog
import android.database.sqlite.SQLiteDatabase
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.swu.ogg.R
import com.swu.ogg.databinding.FragmentPostBinding
import com.swu.ogg.databinding.PostItemBinding
import com.swu.ogg.dbHelper

class PostFragment : DialogFragment() {

    private var _binding : FragmentPostBinding? = null
    private val binding get() = _binding!!

    lateinit var dbManager : dbHelper
    lateinit var sqlDB : SQLiteDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root

       return root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}