package com.example.lenovo.ffmpegdemo

import android.annotation.TargetApi
import android.app.ActivityManager
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import wseemann.media.FFmpegMediaMetadataRetriever
import java.io.File

class MainActivity : AppCompatActivity() {

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
            loadVideoThumb()
        else
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==1){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                loadVideoThumb()
        }
    }

    private fun loadVideoThumb() {
        val filePath = Environment.getExternalStorageDirectory().path + "/DCIM/Camera/AT20191014150845088.mp4"
        if (File(filePath).exists()){
            val mmr = FFmpegMediaMetadataRetriever()
            mmr.setDataSource(filePath)
            val bitmap = mmr.getFrameAtTime(1)
            image_view.setImageBitmap(bitmap)
            mmr.release()
        }
    }
}
