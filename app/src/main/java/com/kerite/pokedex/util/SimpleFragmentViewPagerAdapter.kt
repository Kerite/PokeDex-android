package com.kerite.pokedex.util

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


/**
 * @param fragmentMap key is ResId, value is fragment producer
 */
class SimpleFragmentViewPagerAdapter(
    fa: FragmentActivity,
    val fragmentMap: HashMap<Int, () -> Fragment>
) : FragmentStateAdapter(fa) {
    private val mNavigationIdList: MutableList<Int> = mutableListOf()

    init {
        for (item in fragmentMap) {
            mNavigationIdList.add(item.key)
        }
    }

    override fun getItemCount(): Int {
        return fragmentMap.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentMap[getResId(position)]!!.invoke()
    }

    fun getPosition(@IdRes navId: Int): Int {
        return mNavigationIdList.indexOf(navId)
    }

    fun getResId(position: Int): Int {
        return mNavigationIdList[position]
    }
}