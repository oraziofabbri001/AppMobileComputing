package com.src.appmobilecomputing.ui.orarilezioni

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrariLezioniViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Orari Lezioni"
    }
    val text: LiveData<String> = _text
}