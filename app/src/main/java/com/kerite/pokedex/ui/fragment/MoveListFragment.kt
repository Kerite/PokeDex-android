package com.kerite.pokedex.ui.fragment

import android.os.Bundle
import android.view.View
import com.kerite.fission.android.BaseFragment
import com.kerite.pokedex.databinding.FragmentMoveListBinding

class MoveListFragment : BaseFragment<FragmentMoveListBinding>(
    FragmentMoveListBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}