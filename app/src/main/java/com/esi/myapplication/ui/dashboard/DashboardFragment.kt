package com.esi.myapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.esi.myapplication.Navigator
import com.esi.myapplication.R
import com.esi.myapplication.ui.DetailFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonNextPage.setOnClickListener {
            (activity as? Navigator)?.navigateInTab(
                DetailFragment.newInstance(
                    0,
                    "DashBoard Fragment"
                ), "DetailFrag"
            )
        }
    }

}