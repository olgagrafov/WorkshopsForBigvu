package com.olgag.workshopsbigvu.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.olgag.workshopsbigvu.R
import com.olgag.workshopsbigvu.adapter.MyWorkShopAdapter
import com.olgag.workshopsbigvu.common.Common
import com.olgag.workshopsbigvu.model.Workshop
import com.olgag.workshopsbigvu.my_services.RetrofitServices
import dmax.dialog.SpotsDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    val DELAY_INTERVAL :Long = 300L
    lateinit var mService: RetrofitServices
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyWorkShopAdapter
    lateinit var searchDescription: SearchView
    lateinit var mRecyclerWorkshopList: RecyclerView
    var mdialog: android.app.AlertDialog? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.btnMainClose).setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                finish()
            }
        })

        if(isOnline(this))
            init()
        else
            Toast.makeText(baseContext, getString(R.string.dont_have_internet_connection), Toast.LENGTH_LONG).show()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                    return true
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                    return true
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                    return true
            }
        }
       return false
    }


    override fun onQueryTextSubmit(query: String): Boolean {
           adapter.filter.filter(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
             debounceSearch(newText)
        return true
    }

    private fun debounceSearch(txtForSearch: String) = runBlocking {
        launch {
            delay(DELAY_INTERVAL)
            adapter.filter.filter(txtForSearch)
        }
    }

    private fun init(){
        Common.setUrl(getString(R.string.workshops_url))
        mService = Common.retrofitService

        searchDescription = findViewById(R.id.searchViewDescription)
        searchDescription.setOnQueryTextListener(this@MainActivity)
        searchDescription.visibility = VISIBLE

        layoutManager = LinearLayoutManager(this)
        mRecyclerWorkshopList = findViewById(R.id.recyclerWorkshopList)
        mRecyclerWorkshopList.setHasFixedSize(true)
        mRecyclerWorkshopList.layoutManager = layoutManager

        mdialog = SpotsDialog.Builder().setCancelable(true).setContext(this).build()

        getAllWorkshopsList()

        //checkAdapter()

    }

    private fun getAllWorkshopsList() {
        mdialog?.show()

        mService.getWorkshopsJSONList(getString(R.string.workshops_json)).enqueue(object : Callback<MutableList<Workshop>> {
            override fun onFailure(call: Call<MutableList<Workshop>>, t: Throwable) {

                Toast.makeText(baseContext, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<MutableList<Workshop>>, response: Response<MutableList<Workshop>>) {
                if(response.code() == 200)
                    setDataIntoAdapter( response.body() as MutableList<Workshop?>)
                else
                    Toast.makeText(baseContext, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()

                mdialog?.dismiss()
            }
        })
    }

    private fun setDataIntoAdapter(dataList: MutableList<Workshop?>) {
        var sortedByName: MutableList<Workshop?> = mutableListOf<Workshop?>()
        sortedByName = dataList.sortedBy { myObject -> myObject?.name } as MutableList<Workshop?>
        adapter = MyWorkShopAdapter(baseContext, sortedByName )
        adapter.notifyDataSetChanged()
        mRecyclerWorkshopList.adapter = adapter
    }


    private fun checkAdapter(){

                   val myList: MutableList<Workshop?> = mutableListOf<Workshop?>()
                    myList.add(Workshop("sfdfdsf", "dfsd", "dfdsg", "dfdsg", "fgfgfdg"))
                    myList.add(Workshop("sfdfdsf", "dfsd", "dfdsg dgffsfdgdf fdgfdgfdg dfgdfgfdgfdg fdgfdgbfdgfdg dfgdf ffgfgfdgfdg fdbgfdgfdgfdg fdbgfdgfg fdg ffgbfd dfgfdgfdgfdgdfgdfgdg", "dfdsg", "fgfgfdg"))
                    myList.add(Workshop("sfdfdsf", "dfsd", "dfdsg", "dfdsg", "fgfgfdg"))
                    myList.add(Workshop("sfdfdsf", "dfsd", "dfdsg", "dfdsg", "fgfgfdg"))
        myList.add(Workshop("sfdfdsf", "dfsd", "dfdsg dgffsfdgdf fdgfdgfdg dfgdfgfdgfdg fdgfdgbfdgfdg dfgdf ffgfgfdgfdg fdbgfdgfdgfdg fdbgfdgfg fdg ffgbfd dfgfdgfdgfdgdfgdfgdg", "dfdsg", "fgfgfdg"))
        myList.add(Workshop("sfdfdsf", "dfsd", "dfdsg", "dfdsg", "fgfgfdg"))
        myList.add(Workshop("sfdfdsf", "dfsd", "dfdsg", "dfdsg", "fgfgfdg"))
        myList.add(Workshop("sfdfdsf", "dfsd", "dfdsg dgffsfdgdf fdgfdgfdg dfgdfgfdgfdg fdgfdgbfdgfdg dfgdf ffgfgfdgfdg fdbgfdgfdgfdg fdbgfdgfg fdg ffgbfd dfgfdgfdgfdgdfgdfgdg", "dfdsg", "fgfgfdg"))
        myList.add(Workshop("sfdfdsf", "dfsd", "dfdsg", "dfdsg", "fgfgfdg"))
        myList.add(Workshop("sfdfdsf", "dfsd", "dfdsg", "dfdsg", "fgfgfdg"))
                    adapter = MyWorkShopAdapter(baseContext, myList)
        adapter.notifyDataSetChanged()


        mRecyclerWorkshopList.adapter = adapter
    }
}




