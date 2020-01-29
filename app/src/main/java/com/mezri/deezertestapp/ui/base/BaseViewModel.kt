package com.mezri.deezertestapp.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mezri.deezertestapp.data.errors.AppInformation

abstract class BaseViewModel : ViewModel() {

    protected val TAG = this::class.java.simpleName

    private val _informationToShow = MutableLiveData<AppInformation>().apply { value = null }
    // live data for UI to show app messages
    val informationToShow: LiveData<AppInformation> = _informationToShow

    /**
     * Notify the UI that a new app message is triggered
     */
    fun handleAppMessage(appInformation: AppInformation) {
        _informationToShow.value = appInformation
    }

    open fun clearTemporaryData() {
        _informationToShow.value = null
    }
}