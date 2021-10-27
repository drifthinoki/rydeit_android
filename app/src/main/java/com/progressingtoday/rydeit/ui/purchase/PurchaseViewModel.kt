package com.progressingtoday.rydeit.ui.purchase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PurchaseViewModel: ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Purchase Fragment"
    }
    val text: LiveData<String> = _text

}