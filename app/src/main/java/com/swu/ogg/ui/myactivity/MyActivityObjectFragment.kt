//package com.swu.ogg.ui.myactivity
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.viewpager2.adapter.FragmentStateAdapter
//import com.swu.ogg.databinding.FragmentCollectionObjectBinding
//
//private const val ARG_OBJECT = "object"
//
//class MyActivityObjectFragment : Fragment() {
//
//    private var _binding: FragmentCollectionObjectBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        _binding = FragmentCollectionObjectBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        return root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
//
//            val textView : TextView = binding.textObject1
//            textView.text = getInt(ARG_OBJECT).toString()
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    class MyActivityAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
//
//        override fun getItemCount(): Int = 2
//
//        override fun createFragment(position: Int): Fragment {
//
//            val fragment = MyActivityObjectFragment()
//            fragment.arguments = Bundle().apply {
//                putInt(ARG_OBJECT, position + 1)
//            }
//            return fragment
//        }
//    }
//}
//
