package com.example.testbottomnav.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.testbottomnav.R
import com.example.testbottomnav.navigator.StackNavigator

class DashboardFragment : Fragment() {

    private val stackNavigator by lazy {
        StackNavigator(
            childFragmentManager,
            R.id.dashboardFragmentRoot
        ) {
            InnerDashboardFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)

        textView.setOnClickListener {
            stackNavigator.pushFragment(R.id.inner_dashboard)
        }
        return root
    }

}