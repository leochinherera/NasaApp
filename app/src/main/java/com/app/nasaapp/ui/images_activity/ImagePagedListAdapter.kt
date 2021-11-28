package com.app.nasaapp.ui.images_activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.nasaapp.R
import com.app.nasaapp.data.repository.NetworkState
import com.bumptech.glide.Glide



import com.app.nasaapp.data.vo.Item
import com.app.nasaapp.ui.single_image_details.SingleImage
import kotlinx.android.synthetic.main.image_list_item.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*


class ImagePagedListAdapter(private val context: Context) : PagedListAdapter<Item, RecyclerView.ViewHolder>(
    MovieDiffCallback()
) {

    val IMAGE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == IMAGE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.image_list_item, parent, false)
            return ImageItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == IMAGE_VIEW_TYPE) {
            (holder as ImageItemViewHolder).bind(getItem(position),context)
        }
        else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            IMAGE_VIEW_TYPE
        }
    }




    class MovieDiffCallback : DiffUtil.ItemCallback<Item>() {

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {

            return oldItem.href == newItem.href
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }


    class ImageItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {



        fun bind(it: Item?, context: Context) {
            val imagetitle = StringBuilder()
            val center=StringBuilder()
            val dateCreated =StringBuilder()
            val id=StringBuilder()

            for (i in it?.mydata?.indices!!) {
                val datacenter =  it.mydata[i].center
                val dataDateCreated =  it.mydata[i].dateCreated
                val myTitle =  it.mydata[i].title
                val newId =  it.mydata[i].nasaId

                imagetitle.append(myTitle)
                center.append( datacenter)
                dateCreated.append(dataDateCreated)
                id.append(newId )
            }
            itemView.resource_image_tittle.text = imagetitle.toString()
            itemView.resource_release_date.text =dateCreated.toString()
            itemView.image_center.text =center.toString()

           val posterPath=StringBuilder()
              for (i in it.links.indices) {
                   val pic= it.links[i].href
                        posterPath.append(pic)
            }
               val moviePosterURL = posterPath.toString()
                    Glide.with(itemView.context)
                       .load(moviePosterURL)
                          .into(itemView.cv_iv_image_poster);

            itemView.setOnClickListener{
               val   imageId = id.toString()
                val intent = Intent(context, SingleImage::class.java)
                intent.putExtra("href", imageId)

                context.startActivity(intent)
            }

        }

    }

    class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.progress_bar_item.visibility = View.VISIBLE;
            }
            else  {
                itemView.progress_bar_item.visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            }
            else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            }
            else {
                itemView.error_msg_item.visibility = View.GONE;
            }
        }
    }


    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }

    }




}