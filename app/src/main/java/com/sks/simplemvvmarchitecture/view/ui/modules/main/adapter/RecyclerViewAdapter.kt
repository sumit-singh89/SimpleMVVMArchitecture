package com.sks.simplemvvmarchitecture.view.ui.modules.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sks.simplemvvmarchitecture.R
import com.sks.simplemvvmarchitecture.model.Canada
import com.sks.simplemvvmarchitecture.utils.UtilsMethods
import kotlinx.android.synthetic.main.list_item.view.*

/**
 * @author  Sumit Singh on 8/12/2020.
 */
class RecyclerViewAdapter(private val mContext: Context, private val mList: List<Canada.Row>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTitle: TextView? = view.title
        val mDescription: TextView? = view.description
        val mItemImage: ImageView? = view.imageView
        val mFwd: ImageView? = view.imageArrow
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int): Unit = holder?.let {
        if(mList[position].title != null){
            it.mTitle?.text = mList[position].title
        }else{
            it.mTitle?.text = mContext.getString(R.string.no_title)
        }
        if(mList[position].description != null){
            it.mDescription?.text = mList[position].description
        }else{
            it.mDescription?.text = mContext.getString(R.string.demo_description)
        }
        val url = mList[position].imageHref

        it.mItemImage?.let { it1 ->
            Glide.with(mContext) //1
                .load(url)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_24)
                .into(it1)
        }
        it.mFwd?.setOnClickListener {
            val message  = """$position position item is clicked."""
            UtilsMethods.showToast(mContext,message)
        }
    }


}