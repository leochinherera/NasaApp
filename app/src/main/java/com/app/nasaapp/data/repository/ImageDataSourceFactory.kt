package com.app.nasaapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.app.nasaapp.data.api.ImageInterface
import com.app.nasaapp.data.vo.Item
import io.reactivex.disposables.CompositeDisposable
//class responsible setting live data on the application
class ImageDataSourceFactory (private val apiService : ImageInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Item>() {

    val moviesLiveDataSource =  MutableLiveData<ImageDataSource>()
//set Live data on Item
    override fun create(): DataSource<Int, Item> {
        val movieDataSource = ImageDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}