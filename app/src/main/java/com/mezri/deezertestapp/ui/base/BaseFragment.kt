package com.mezri.deezertestapp.ui.base

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        // init observers
        initObservers()
    }

    override fun onPause() {
        super.onPause()
        removeObservers()
        getFragmentViewModel().clearTemporaryData()
    }

    abstract fun initObservers()
    abstract fun removeObservers()
    abstract fun getFragmentViewModel(): BaseViewModel
}