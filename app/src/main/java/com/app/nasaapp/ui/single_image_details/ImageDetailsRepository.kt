package com.app.nasaapp.ui.single_image_details

import androidx.lifecycle.LiveData
import com.app.nasaapp.data.api.ImageInterface
import com.app.nasaapp.data.repository.ImageDetailsNetworkDataSource
import com.app.nasaapp.data.repository.NetworkState
import com.app.nasaapp.data.vo.ImageResponse
import io.reactivex.disposables.CompositeDisposable

class ImageDetailsRepository (private val apiService : ImageInterface) {

    lateinit var imageDetailsNetworkDataSource: ImageDetailsNetworkDataSource

    fun fetchSingleImageDetails (compositeDisposable: CompositeDisposable, nasaId:String) : LiveData<ImageResponse> {

        imageDetailsNetworkDataSource = ImageDetailsNetworkDataSource(apiService,compositeDisposable)
        imageDetailsNetworkDataSource.fetchImageDetails(nasaId)

        return imageDetailsNetworkDataSource.downloadedImageResponse

    }

    fun getImageDetailsNetworkState(): LiveData<NetworkState> {
        return imageDetailsNetworkDataSource.networkState
    }



}