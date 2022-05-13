package com.coder.challengechapter5binar.viewmodel

import androidx.lifecycle.*
import com.coder.challengechapter5binar.datastore.UserDataStoreManager
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserDataStoreManager) : ViewModel() {

    fun saveDataStore(value: String) {
        viewModelScope.launch {
            pref.saveUserPref(value)
        }
    }

    fun getDataStore(): LiveData<String> {
        return pref.getUserPref().asLiveData()
    }
}