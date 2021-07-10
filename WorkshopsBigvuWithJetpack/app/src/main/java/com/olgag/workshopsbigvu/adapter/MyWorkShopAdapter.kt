package com.olgag.workshopsbigvu.adapter

import android.content.Context
import android.content.Intent

import android.graphics.BitmapFactory
import android.os.Build

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.size.ViewSizeResolver

import com.olgag.workshopsbigvu.R
import com.olgag.workshopsbigvu.activities.ComposeWorkshopDetails
import com.olgag.workshopsbigvu.activities.WorkshopDetails
import com.olgag.workshopsbigvu.model.Workshop


class MyWorkShopAdapter (
    private val context: Context,
    private val workShopList: MutableList<Workshop?>):
    RecyclerView.Adapter<MyWorkShopAdapter.MyViewHolder>(),
    Filterable {

    var searchableList: MutableList<Workshop?> = mutableListOf<Workshop?>()

    init {
        searchableList = workShopList
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image_worker)
        val txt_name: TextView = itemView.findViewById(R.id.txt_name)
        val txt_description: TextView = itemView.findViewById(R.id.txt_description)

        fun bind(listItem: Workshop) {
            itemView.setOnClickListener {
               val intent = Intent(it.context, WorkshopDetails::class.java)
                intent.putExtra("workshop", listItem)
                it.context.startActivity(intent)
            }
            image.setOnClickListener{
                val intent = Intent(it.context, ComposeWorkshopDetails::class.java)
                intent.putExtra("workshop", listItem)
                it.context.startActivity(intent)
          }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = searchableList.size

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listItem = searchableList[position]
        listItem?.let { holder.bind(it) }

        setCoilPicFromUrl(holder.image, listItem?.image)

        holder.txt_name.text = listItem?.name
        holder.txt_description.text = listItem?.description
    }

       override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty())
                    searchableList = workShopList
                else {
                        var filteredList: MutableList<Workshop?> = mutableListOf<Workshop?>()
                    for (row in workShopList) {
                        if (row?.description!!.contains(constraint.toString(), ignoreCase = true)) {
                            filteredList.add(row)
                        }
                    }
                    searchableList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = searchableList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchableList  = results?.values as MutableList<Workshop?>
                notifyDataSetChanged()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setCoilPicFromUrl(imageView: ImageView, imageUrl: String?) {

        if(!URLUtil.isValidUrl(imageUrl))
            imageView.setImageBitmap(BitmapFactory.decodeResource(context.resources,R.drawable.no_pic))
        else {

            //spinner works until image load
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 20f
            circularProgressDrawable.centerRadius = 60f
            circularProgressDrawable.setColorSchemeColors(context.resources.getColor(R.color.purple_700))
            circularProgressDrawable.start()

            // enqueue
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .placeholder(circularProgressDrawable)
                .size(ViewSizeResolver(imageView))
                .target(imageView)
                .build()
            ImageLoader(context).enqueue(request)
            //add frame for image
            imageView.foreground= context.getDrawable(R.drawable.shape_border)
        }
    }
}



/*
   private fun setBitmapFromUrl(imageView: ImageView, imageUrl: String?, progressBar: ProgressBar) {
       progressBar.visibility = VISIBLE
       imageView.visibility = GONE
       CoroutineScope(Dispatchers.IO).launch {
           val bitmap = getBitmap(imageUrl)

           withContext(Dispatchers.Main) {
               imageView.setImageBitmap(bitmap)
               progressBar.visibility = GONE
               imageView.visibility = VISIBLE
           }
       }
   }

   private fun getBitmap(imageUrl: String?): Bitmap? {
       if(URLUtil.isValidUrl(imageUrl))
           return BitmapFactory.decodeStream(URL(imageUrl).openConnection().getInputStream())
       else
           return BitmapFactory.decodeResource(context.resources,R.drawable.no_pic)
   }

    */

