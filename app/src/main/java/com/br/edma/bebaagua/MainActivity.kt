package com.br.edma.bebaagua


import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {

    lateinit var btnNotify : Button
    lateinit var editMinutes : EditText
    lateinit var timePicker : TimePicker
    lateinit var preferences : SharedPreferences

    var hours : Int = 0
    var minute : Int = 0
    var interval : Int = 0

    private var activated : Boolean = false


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNotify  = findViewById(R.id.btn_notify)
        editMinutes = findViewById(R.id.edit_txt_number)
        timePicker = findViewById(R.id.time_picker)

        timePicker.setIs24HourView(true)
        preferences  = getSharedPreferences("db", Context.MODE_PRIVATE)

        activated = preferences.getBoolean("activated", false)


        if(activated){
            btnNotify.setText(R.string.pause)
            btnNotify.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black))
            activated = true

            val interval  = preferences.getInt("interval" , 0)
            val hours = preferences.getInt("hours", timePicker.hour)
            val minutes = preferences.getInt("minute", timePicker.minute)

            editMinutes.setText(interval.absoluteValue)
            timePicker.hour = hours
            timePicker.minute = minutes

        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun notifyClick(view : View){
        val sInterval = editMinutes.text.toString()

        if(sInterval.isEmpty()){
            Toast.makeText(this, R.string.erro_msg, Toast.LENGTH_SHORT).show()
            return
        }

        hours = timePicker.hour
        minute = timePicker.minute
        interval = Integer.parseInt(sInterval)
        
        if(!activated) {
            btnNotify.setText(R.string.pause)
            btnNotify.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black))
            activated = true

            val editor : SharedPreferences.Editor = preferences.edit()
            editor.putBoolean("activated", true)
            editor.putInt("interval", interval)
            editor.putInt("hours", hours)
            editor.putInt("minute", minute)
            editor.apply()

        } else{
            btnNotify.setText(R.string.notify)
            btnNotify.setBackgroundColor(ContextCompat.getColor(this, R.color.design_default_color_primary))
            activated = false

            val editor : SharedPreferences.Editor = preferences.edit()
            editor.putBoolean("activated", false)
            editor.remove("interval")
            editor.remove("hours")
            editor.remove("minute")
            editor.apply()

        }

    }


}



