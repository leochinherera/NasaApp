package com.app.nasaapp.ui.single_image_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.app.nasaapp.R
import com.bumptech.glide.Glide
import com.app.nasaapp.data.api.ImageRetrofitClass
import com.app.nasaapp.data.api.ImageInterface
import com.app.nasaapp.data.repository.NetworkState
import com.app.nasaapp.data.vo.ImageResponse
import kotlinx.android.synthetic.main.activity_single_image.*


class SingleImage : AppCompatActivity() {

    private lateinit var viewModel: SingleImageViewModel
    private lateinit var imageRepository: ImageDetailsRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_image)

supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val nasaId: String = intent.getStringExtra("href").toString()

        val apiService : ImageInterface = ImageRetrofitClass.getClient()
        imageRepository = ImageDetailsRepository(apiService)

        viewModel = getViewModel(nasaId)

        viewModel.imageDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })

    }

    fun bindUI( it: ImageResponse?){
        val imagetitle = StringBuilder()
             val description=StringBuilder()
               val center=StringBuilder()
                   val dateCreated =StringBuilder()
                       val imageItem=it?.collectionsItem?.items
          for (i in imageItem?.indices!!) {
               val datacenter =  imageItem[i].mydata
                    for(x in datacenter.indices){
                        val dataDateCreated = datacenter[x].dateCreated
                            val dataDescrption =  datacenter[x]. description
                                val myTitle = datacenter[x].title
                        val imageCenter = datacenter[x].center
                                     imagetitle.append(myTitle)
                                   description.append(dataDescrption)
                                center.append(imageCenter)
                        dateCreated.append(dataDateCreated )
            }

        }

        image_title.text = imagetitle.toString()
        image_center.text = center.toString()
        image_release_date.text = dateCreated.toString()
        image_overview.text = description.toString()

        val posterPath=StringBuilder()
        for (image in imageItem.indices) {
            val poster=imageItem[image].links
            for (x in poster.indices){
             val posteritem=poster[x].href
                posterPath.append(posteritem) }
            }

        val posterUrl=posterPath.toString()
        Glide.with(this)
            .load(posterUrl )
            .into(iv_image_poster);

    }


    private fun getViewModel(nasaId:String): SingleImageViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleImageViewModel(imageRepository,nasaId) as T
            }
        })[SingleImageViewModel::class.java]
    }
}

