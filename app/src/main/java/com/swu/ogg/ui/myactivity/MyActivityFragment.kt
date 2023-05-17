package com.swu.ogg.ui.myactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.swu.ogg.databinding.FragmentMyactivityBinding

// 나의 활동 전체 레이아웃 구현부

class MyActivityFragment : Fragment() {

    private var _binding: FragmentMyactivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myActivityViewModel =
            ViewModelProvider(this).get(MyActivityViewModel::class.java)

        _binding = FragmentMyactivityBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textTitleMyactivity
//        myActivityViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}