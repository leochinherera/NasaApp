package com.app.nasaapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.nasaapp.data.api.ImageInterface
import com.app.nasaapp.data.vo.ImageResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ImageDetailsNetworkDataSource (private val apiService : ImageInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState                   //with this get, no need to implement get function to get networkSate

    private val _downloadedImageDetailsResponse =  MutableLiveData<ImageResponse>()
    val downloadedImageResponse: LiveData<ImageResponse>
        get() = _downloadedImageDetailsResponse

    fun fetchImageDetails(nasaId: String) {

        _networkState.postValue(NetworkState.LOADING)


        try {

            compositeDisposable.add(
                apiService.getImageDetails(nasaId)//get an image by id on the repository
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedImageDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)

                        }
                    )
            )

        }

        catch (e: Exception){

        }


    }
}