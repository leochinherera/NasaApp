package com.app.nasaapp.ui.images_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.app.nasaapp.data.repository.NetworkState
import com.app.nasaapp.data.vo.Item
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val imageRepository : ImagePagedListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  imagePagedList : LiveData<PagedList<Item>> by lazy {
        imageRepository.fetchLiveimagePagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkState> by lazy {
        imageRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return imagePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}