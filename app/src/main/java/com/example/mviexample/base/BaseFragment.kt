package com.example.mviexample.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseFragment<Binding: ViewDataBinding, State, Event>(@LayoutRes val layoutId: Int): Fragment() {

    abstract val viewModel: BaseViewModel<State, Event>

    private var _binding: Binding? = null
    private val binding: Binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    protected fun bind(lambda: Binding.() -> Unit) {
        lambda(binding)
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