package com.esi.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.esi.myapplication.Navigator
import com.esi.myapplication.R
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        message.text = "Number: ${getPageNumber()} Parent: ${getParentName()}"

        buttonNextPage.setOnClickListener {
            (activity as? Navigator)?.navigateInTab(
                newInstance(
                    getPageNumber()?.plus(1) ?: 1,
                    DetailFragment::class.java.simpleName
                ),
                "${this::class.java.simpleName}:${getPageNumber()}"
            )
        }
    }

    private fun getParentName() = arguments?.getString(PARENT)
    private fun getPageNumber() = arguments?.getInt(NUMBER)

    companion object {
        private const val NUMBER = "number"
        private const val PARENT = "parent"
        private const val TAG = "DetailFragment"

        fun newInstance(number: Int, parent: String): DetailFragment {
            return DetailFragment().apply {
                val bundle = Bundle()
                bundle.putInt(NUMBER, number)
                bundle.putString(PARENT, parent)
                arguments = bundle
            }
        }
    }
}