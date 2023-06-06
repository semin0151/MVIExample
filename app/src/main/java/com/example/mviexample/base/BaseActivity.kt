package com.example.mviexample.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseActivity<Binding: ViewDataBinding, State, Event>(@LayoutRes val layoutId: Int): AppCompatActivity() {

    abstract val viewModel: BaseViewModel<State, Event>

    private var _binding: Binding? = null
    private val binding: Binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutId)
        initData(savedInstanceState)
        initViewModel()
        initView()
    }

    protected fun bind(block: Binding.() -> Unit) {
        block(binding)
    }

    private fun initViewModel() {
        bind {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.state.collect { state ->
                    render(state)
                }
            }
        }
    }

    abstract fun initData(bundle: Bundle?)
    abstract fun initView()
    protected open fun render(state: State) {}
}