package com.olgag.workshopsbigvu.activities


import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.olgag.workshopsbigvu.R
import com.olgag.workshopsbigvu.model.Workshop


class WorkshopDetails : AppCompatActivity() {
    lateinit var videoView : VideoView
    lateinit var uri: Uri
    lateinit var txtName: TextView
    lateinit var txtDescription: TextView
    lateinit var txtText: TextView
    lateinit var ctlr: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workshop_details)

        init()
    }

    override fun onStart() {
        super.onStart()

        videoView!!.setVideoURI(uri)
        videoView!!.requestFocus()
        videoView!!.setMediaController(ctlr)
        videoView!!.start()
    }
    //stop videoView when exit from activity
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            videoView.stopPlayback()
            finish()
            return true;
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun init(){

        findViewById<ImageView>(R.id.btnDetalisClose).setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                finish()
            }
        })

        val workshop = intent.getParcelableExtra<Workshop>("workshop")

        txtName = findViewById(R.id.textViewName)
        txtName.text = workshop?.name

        txtText = findViewById(R.id.textViewNText)
        txtText.text = workshop?.text

        txtDescription = findViewById(R.id.textViewDescription)
        txtDescription.text = workshop?.description

        ctlr =  MediaController(this)
        videoView= findViewById(R.id.workshopVideo)
        uri = Uri.parse(workshop?.video)
    }
}