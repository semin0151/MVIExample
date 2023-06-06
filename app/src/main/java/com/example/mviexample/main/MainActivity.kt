package com.example.mviexample.main

import androidx.lifecycle.lifecycleScope
import com.example.mviexample.R
import com.example.mviexample.Semin
import com.example.mviexample.base.BaseActivity
import com.example.mviexample.databinding.ActivityMainBinding
import com.example.mviexample.util.gone
import com.example.mviexample.util.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding, MainState>(layoutId = R.layout.activity_main) {
    private val viewModel = MainViewModel()

    override fun initView() {
        bind {
            vm = viewModel

            semin.btnError.setOnClickListener {
                viewModel.onInitEvent()
            }
        }
    }

    override fun initCollect() {
        bind {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.state.collect { state ->
                    Semin.messageLog("$state")
                    updateView(state)
                }
            }
        }
    }

    override fun updateView(state: MainState) {
        bind {
            if(state.isLoading) {
                semin.root.gone()
                pbLoading.visible()
            } else if(state.isError) {
                semin.root.visible()
                pbLoading.gone()
            } else {
                semin.root.gone()
                pbLoading.gone()
                tvTest.text = state.count.toString()
            }
        }
    }
}