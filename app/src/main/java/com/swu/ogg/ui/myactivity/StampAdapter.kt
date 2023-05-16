package com.swu.ogg.ui.myactivity

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.swu.ogg.R

private val TAB_LABELS = arrayOf(
    R.string.tab_label1,
    R.string.tab_label2
)
class StampAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_LABELS[position])
    }
    override fun getCount(): Int {
        return 2
    }


}