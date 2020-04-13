@file:Suppress("DEPRECATION")

package com.example.moon

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.moon.Algorithms.algorithmOfChoice
import com.example.moon.Settings.algo
import com.example.moon.Settings.filename
import com.example.moon.Settings.hemisphere
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var currentYear: Int = Calendar.getInstance().get(Calendar.YEAR);
    var currentMonth: Int = Calendar.getInstance().get(Calendar.MONTH)+1;//miesiące indeksowane są od 0
    var currentDay: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    private var percentage:Int = 0
    private var moonDay:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initStorageSettings()
    }
    override fun onResume() {
        super.onResume()
        initMain()
    }
    private fun initMain(){
        moonDay = algorithmOfChoice(algo,currentYear,currentMonth,currentDay)
        initPercentage()
        initInter()
        initFull()
        imageSelector()
    }
    private fun initStorageSettings(){
        if(!FileExists(filename)){
            val file = OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE))
            file.write("n\ntrig1")
            file.flush()
            file.close()
            //Toast.makeText(this,"Utworzono settings.txt",Toast.LENGTH_LONG).show()
        }
        var settingsLines = ArrayList<String>()
        try{
            if(FileExists(filename)){
                val file = InputStreamReader(openFileInput(filename))
                val br = BufferedReader(file)
                var line = br.readLine()
                while(line!=null){
                    settingsLines.add(line)
                    line = br.readLine()
                }
                file.close()
                val count = settingsLines.size
                //Toast.makeText(this,"Wczytano $count linii ustawień",Toast.LENGTH_LONG).show()
                if(count==2){
                    hemisphere=settingsLines[0]
                    algo=settingsLines[1]
                }
            }
            else {
                //Toast.makeText(this,"Nie wczytano nic :(",Toast.LENGTH_LONG).show()
            }
        }catch (e:Exception){}
    }

    private fun FileExists(path:String):Boolean{
        val file = baseContext.getFileStreamPath(path)
        return file.exists()
    }
    private fun initPercentage() {
        percentage = ((moonDay/30.0) *100.0).toInt()
        today.text=("Dzisiaj: $percentage%")
    }
    private fun initInter(){
        var calendar:Calendar = Calendar.getInstance()
        var x=moonDay.toInt()
        while (x!=0)
        {
            calendar.add(Calendar.DAY_OF_YEAR,-1)
            x--
        }
        prevInter.text=("Poprzedni nów: ${calendar.get(Calendar.DAY_OF_MONTH).toString()}." +
                "${(calendar.get(Calendar.MONTH)+1).toString()}." +
                "${calendar.get(Calendar.YEAR).toString()}")
    }
    private fun initFull(){
        var calendar:Calendar = Calendar.getInstance()
        var x=moonDay.toInt()
        do
        {
            calendar.add(Calendar.DAY_OF_YEAR,1)
            x++
            if(x==29)x=0
        }while (x!=15)
        nextFull.text=("Następna pełnia: ${calendar.get(Calendar.DAY_OF_MONTH).toString()}." +
                "${(calendar.get(Calendar.MONTH)+1).toString()}." +
                "${calendar.get(Calendar.YEAR).toString()}")
    }
    private fun imageSelector() {
        val imgName:String = "$hemisphere${moonDay.toInt()}"
        //Toast.makeText(this,"$imgName",Toast.LENGTH_LONG).show()
        val imgId = resIdByName(imgName,"drawable")
        moonView.setImageResource(imgId)
    }
    private fun Context.resIdByName(resIdName: String?, resType: String): Int {
        resIdName?.let {
            return resources.getIdentifier(it, resType, packageName)
        }
        throw Resources.NotFoundException()
    }
    fun toFull(v:View) {
        val i= Intent(this,SecondActivity::class.java)
        startActivity(i)
    }
    fun toSettings(v:View) {
        val i= Intent(this,SettingsActivity::class.java)
        startActivity(i)
    }

}
