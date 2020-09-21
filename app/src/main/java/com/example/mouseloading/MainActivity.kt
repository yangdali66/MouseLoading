package com.example.mouseloading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

mStart1.setOnClickListener(){
    mouse.startAnim()
    purse.startAnim1()
}

        mStop.setOnClickListener(){
            mouse.stopAnim()
            purse.stopAnim1()
        }
    }


}