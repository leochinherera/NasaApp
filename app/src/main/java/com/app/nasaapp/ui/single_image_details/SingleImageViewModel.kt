package com.app.nasaapp.ui.single_image_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.nasaapp.data.repository.NetworkState
import com.app.nasaapp.data.vo.ImageResponse
import io.reactivex.disposables.CompositeDisposable

class SingleImageViewModel (private val imageRepository : ImageDetailsRepository, nasaId:String)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  imageDetails : LiveData<ImageResponse> by lazy {
        imageRepository.fetchSingleImageDetails(compositeDisposable,nasaId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        imageRepository.getImageDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}