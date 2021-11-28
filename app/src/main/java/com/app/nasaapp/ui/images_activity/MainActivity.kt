package com.app.nasaapp.ui.images_activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.app.nasaapp.R

import com.app.nasaapp.data.api.ImageRetrofitClass
import com.app.nasaapp.data.api.ImageInterface
import com.app.nasaapp.data.repository.NetworkState


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    lateinit var imageRepository: ImagePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService : ImageInterface = ImageRetrofitClass.getClient()

        imageRepository = ImagePagedListRepository(apiService)

        viewModel = getViewModel()

        val imageAdapter = ImagePagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 1)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = imageAdapter.getItemViewType(position)
                if (viewType == imageAdapter.IMAGE_VIEW_TYPE) return  1    // IMAGE_VIEW_TYPE will occupy 1 out of 3 span
                else return 1                                             // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        };


        resource_image_list.layoutManager = gridLayoutManager
        resource_image_list.setHasFixedSize(true)
        resource_image_list.adapter = imageAdapter

        viewModel.imagePagedList.observe(this, Observer {
            imageAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                imageAdapter.setNetworkState(it)
            }
        })

    }




    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(imageRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }

}
