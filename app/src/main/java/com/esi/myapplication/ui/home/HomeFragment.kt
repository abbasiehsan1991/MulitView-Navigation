package com.esi.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.esi.myapplication.R
import com.esi.myapplication.ui.DetailFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonNextPage.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainerHome,
                    DetailFragment.newInstance(1, HomeFragment::class.java.simpleName),
                    DetailFragment::class.java.simpleName
                ).commit()
        }
    }
}