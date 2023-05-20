package com.swu.ogg.ui.env

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.swu.ogg.R
import com.swu.ogg.database.NewRecordActivity
import com.swu.ogg.databinding.FragmentEnvBinding
import com.swu.ogg.member.SettingsFragment

class EnvFragment : Fragment() {

    private var _binding: FragmentEnvBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val envViewModel =
            ViewModelProvider(this).get(EnvViewModel::class.java)

        _binding = FragmentEnvBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textEnv
        envViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val envToolbar = binding.envToolbar
        envToolbar.inflateMenu(R.menu.env_menu)

        return root
    }
    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        R.id.navigation_settings -> {
            //findNavController().navigate(R.id.action_navigation_env_to_navigation_settings2)
            true
        }
        R.id.navigation_collections -> {
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}