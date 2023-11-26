package com.turtle.turtlebike.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtle.turtlebike.MainRepository
import com.turtle.turtlebike.model.UBikeModel
import com.turtle.turtlebike.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var repository: MainRepository) : ViewModel() {

    private var TAG = MainViewModel::class.java.simpleName

    //bike資料
    val uBikeData: MutableLiveData<UBikeModel?> = MutableLiveData()

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Event<String>>()
    val isError: LiveData<Event<String>> get() = _isError


    fun callApiBike() {

        _isLoading.value = true

        viewModelScope.launch {
            try {

                val response = repository.callUbikeService()
                if (response.isSuccessful) {

                    // 項目的sna字段中的"YouBike2.0_"字串移除
                    response.body()?.forEach { item ->
                        item.sna = item.sna.replace("YouBike2.0_", "")
                    }

                    // 更新LiveData
                    uBikeData.postValue(response.body())

                    _isLoading.postValue(false)

                } else {

                    _isError.postValue(Event("讀取失敗，請稍後再試 ${response.errorBody().toString()}"))

                }

            } catch (e: Exception) {

                Log.d(TAG, "Error: ${e.localizedMessage}")
                _isError.postValue(Event("讀取失敗，請稍後再試 ${e.localizedMessage}"))

            }
        }
    }
}