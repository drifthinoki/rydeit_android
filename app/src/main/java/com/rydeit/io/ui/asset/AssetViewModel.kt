package com.rydeit.io.ui.asset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AssetViewModel:ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is asset Fragment"
    }
    val text: LiveData<String> = _text
}