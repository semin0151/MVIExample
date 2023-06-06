package com.example.mviexample.ui.main

import android.os.Bundle
import com.example.mviexample.R
import com.example.mviexample.base.BaseActivity
import com.example.mviexample.databinding.ActivityMainBinding
import com.example.mviexample.util.gone
import com.example.mviexample.util.visible

class MainActivity : BaseActivity<ActivityMainBinding, MainState, MainEvent>(layoutId = R.layout.activity_main) {
    override val viewModel = MainViewModel()

    override fun initData(bundle: Bundle?) {
        bind {
            vm = viewModel
        }
    }

    override fun initView() {
        bind {
            layoutDefaultError.btnError.setOnClickListener {
                viewModel.onInitEvent()
            }
        }
    }

    override fun render(state: MainState) {
        bind {
            if(state.isLoading) {
                layoutDefaultError.root.gone()
                pbLoading.visible()
            } else if(state.isError) {
                layoutDefaultError.root.visible()
                pbLoading.gone()
            } else {
                layoutDefaultError.root.gone()
                pbLoading.gone()
                tvTest.text = state.count.toString()
            }
        }
    }
}