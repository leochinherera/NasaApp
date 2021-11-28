package com.app.nasaapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.app.nasaapp.data.api.FIRST_PAGE
import com.app.nasaapp.data.api.ImageInterface
import com.app.nasaapp.data.api.Total_PAGES
import com.app.nasaapp.data.vo.Item

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ImageDataSource (private val apiService : ImageInterface, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, Item>(){

    private var page = FIRST_PAGE
    private var totalPage= Total_PAGES

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Item>) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getImage(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.collectionsItem.items, null, page+1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message!!)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getImage(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(totalPage>= params.key) {
                            callback.onResult(it.collectionsItem.items, params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        }
                        else{
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message!!)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {

    }

    
}