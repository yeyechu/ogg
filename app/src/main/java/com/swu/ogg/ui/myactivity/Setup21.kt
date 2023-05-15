package com.swu.ogg.ui.myactivity

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swu.ogg.R

// 스탬프 레이아웃 구현부

class Setup21 : Fragment() {

    companion object {
        fun newInstance() = Setup21()
    }

    private lateinit var viewModel: Setup21ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setup21, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Setup21ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}