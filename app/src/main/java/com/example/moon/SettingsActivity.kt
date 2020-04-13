package com.example.moon

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initSettings()
    }

    override fun onResume() {
        super.onResume()
        initSettings()
    }

    private fun initSettings()
    {
        when(Settings.algo)
        {
            "conway" -> algoConway.isChecked=true
            "simple" -> algoSimple.isChecked=true
            "trig1" -> algoTrig1.isChecked=true
            "trig2" -> algoTrig2.isChecked=true
        }
        when(Settings.hemisphere)
        {
            "s" -> switchHemi.isChecked=true
            "n" -> switchHemi.isChecked=false
        }
    }
    override fun onPause() {
        super.onPause()
        val file = OutputStreamWriter(openFileOutput(Settings.filename, Context.MODE_PRIVATE))
        file.write("")
        file.flush()
        if (switchHemi.isChecked) file.append("s\n")
        else file.append("n\n")
        if (algoConway.isChecked)file.append("conway")
        if (algoSimple.isChecked)file.append("simple")
        if (algoTrig1.isChecked)file.append("trig1")
        if (algoTrig2.isChecked)file.append("trig2")
        file.flush()
        alterSettings()
    }
    private fun alterSettings()
    {
        var settingsLines = ArrayList<String>()
        try{
            if(FileExists(Settings.filename)){
                val file = InputStreamReader(openFileInput(Settings.filename))
                val br = BufferedReader(file)
                var line = br.readLine()
                while(line!=null){
                    settingsLines.add(line)
                    line = br.readLine()
                }
                file.close()
                val count = settingsLines.size
                if(count==2){
                    Settings.hemisphere =settingsLines[0]
                    Settings.algo =settingsLines[1]
                }
            }
        }catch (e: Exception){}
    }
    private fun FileExists(path:String):Boolean{
        val file = baseContext.getFileStreamPath(path)
        return file.exists()
    }

}

