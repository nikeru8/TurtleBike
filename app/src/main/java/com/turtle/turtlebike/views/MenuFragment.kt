package com.turtle.turtlebike.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.turtle.turtlebike.R
import com.turtle.turtlebike.databinding.FragmentMenuBinding
import com.turtle.turtlebike.model.MenuModel
import com.turtle.turtlebike.utils.viewBinding
import com.turtle.turtlebike.views.adapter.MenuAdapter

class MenuFragment : Fragment() {

    private val binding by viewBinding<FragmentMenuBinding>()


    companion object {

        fun newInstance() = MenuFragment().apply {

            arguments = Bundle().apply {

            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = binding.root
        initData()
        initView()
        initListener()
        return view

    }

    private fun initData() {

        val llm = LinearLayoutManager(requireContext())
        binding.rvMenu.layoutManager = llm
        val modelLists = mutableListOf<MenuModel>()
        //init menu data
        resources.getStringArray(R.array.menu_bike).forEach { content ->

            val model = MenuModel(content, content == "站點資訊")
            modelLists.add(model)

        }
        binding.rvMenu.adapter = MenuAdapter(requireContext(), modelLists, itemClick = {

            Toast.makeText(requireContext(), "menu click - ${it.content}", Toast.LENGTH_SHORT).show()

        })

    }

    private fun initView() {

        binding.apply {


        }

    }

    @SuppressLint("CheckResult")
    private fun initListener() {

        binding.ivCancel.setOnClickListener {

            parentFragmentManager.popBackStack()

        }

        binding.tvLogin.setOnClickListener {

            Toast.makeText(requireContext(), "CLICK LOGIN", Toast.LENGTH_SHORT).show()

        }

    }
}
