package nz.co.test.transactions.features.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeViewPagerAdapter(
    parent: AppCompatActivity,
    private val listFragments: List<Fragment>,
) : FragmentStateAdapter(parent) {
    override fun getItemCount() = listFragments.size

    override fun createFragment(position: Int): Fragment = listFragments[position]

    fun getItem(currentItem: Int): Fragment? = listFragments.getOrNull(currentItem)
}