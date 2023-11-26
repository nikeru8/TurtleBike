package com.turtle.turtlebike.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.turtle.turtlebike.R
import com.turtle.turtlebike.views.adapter.BikeAdapter
import com.turtle.turtlebike.views.adapter.BikeSearchAdapter
import com.turtle.turtlebike.databinding.ActivityMainBinding
import com.turtle.turtlebike.model.UBikeModel
import com.turtle.turtlebike.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var bikeItem: UBikeModel? = null
    private lateinit var adapter: BikeAdapter
    private lateinit var searchAdapter: BikeSearchAdapter
    private var searchStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {

            setContentView(this.root)

        }

        initData()
        initView()
        initListener()
        initObserver()

    }


    @SuppressLint("CheckResult")
    private fun initListener() {

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {


                if (s?.count() ?: 0 > 0) {

                    binding.rvSearch.visibility = View.VISIBLE
                    searchAdapter.updateData(adapter.filter(s.toString()), s.toString())

                } else {

                    binding.rvSearch.visibility = View.GONE

                }

                searchStr = s.toString()
                binding.editTextSearch.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.black))
                binding.ivSearch.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.icon_search))

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 不需要實現
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 不需要實現
            }
        })

        binding.ivSearch.setOnClickListener {

            binding.ivSearch.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_search_green))
            binding.editTextSearch.setTextColor(ContextCompat.getColor(this, R.color.bike_main_color))
            searchAdapter.finalSearchCheck(adapter.filter(searchStr), searchStr)

        }

        binding.ivMenu.setOnClickListener {

            val fragment = MenuFragment.newInstance()
         supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right,  //進入的動畫
                    R.anim.slide_out_left,  //退出的動畫
                    R.anim.slide_in_left,   //返回back
                    R.anim.slide_out_right  //當前 Fragment 退出的動畫（當按下返回鍵時或調用 popBackStack() 時）
                )
                .replace(R.id.flame_fragment, fragment)
                .addToBackStack(null)
                .commit()


        }

    }

    private fun initView() {

        adapter = BikeAdapter(this, bikeItem)
        val llm = LinearLayoutManager(this)
        binding.rvBike.layoutManager = llm
        binding.rvBike.adapter = adapter

        val llmm = LinearLayoutManager(this)
        binding.rvSearch.layoutManager = llmm
        searchAdapter = BikeSearchAdapter(this)
        binding.rvSearch.adapter = searchAdapter

    }

    private fun initObserver() {

        viewModel.uBikeData.observe(this) { model ->

            adapter.updateData(model)

        }

    }

    private fun initData() {

        viewModel.callApiBike()

    }
}