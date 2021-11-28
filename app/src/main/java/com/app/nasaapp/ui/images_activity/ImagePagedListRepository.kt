package com.app.nasaapp.ui.images_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.app.nasaapp.data.api.POST_PER_PAGE
import com.app.nasaapp.data.api.ImageInterface
import com.app.nasaapp.data.repository.ImageDataSource
import com.app.nasaapp.data.repository.ImageDataSourceFactory
import com.app.nasaapp.data.repository.NetworkState
import com.app.nasaapp.data.vo.Item

import io.reactivex.disposables.CompositeDisposable

class ImagePagedListRepository (private val apiService : ImageInterface) {

    lateinit var imagePagedList: LiveData<PagedList<Item>>
    lateinit var imageDataSourceFactory: ImageDataSourceFactory

    fun fetchLiveimagePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Item>> {
        imageDataSourceFactory = ImageDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        imagePagedList = LivePagedListBuilder(imageDataSourceFactory, config).build()

        return imagePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<ImageDataSource, NetworkState>(
            imageDataSourceFactory.moviesLiveDataSource, ImageDataSource::networkState)
    }
}