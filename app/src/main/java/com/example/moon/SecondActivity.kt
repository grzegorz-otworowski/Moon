package com.example.moon


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.moon.Algorithms.algorithmOfChoice
import com.example.moon.Settings.algo
import kotlinx.android.synthetic.main.activity_second.*
import java.util.*

class SecondActivity : AppCompatActivity() {
    var currentYear: Int = Calendar.getInstance().get(Calendar.YEAR);
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        date.setText(currentYear.toString())
    }

    override fun onResume() {
        super.onResume()
        fullDates()
    }
    private fun fullDates() {
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH,1)
        calendar.set(Calendar.MONTH,0)
        calendar.set(Calendar.YEAR,date.text.toString().toInt())
        val textViewL = listOf<TextView>(full0,full1,full2,full3,full4,full5,full6,full7,full8,full9,full10,full11)
        var i = 0
        while (calendar.get(Calendar.YEAR)==date.text.toString().toInt()){
            if(algorithmOfChoice(algo,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).toInt()==15){
                if(i<12)textViewL[i++].text=("${calendar.get(Calendar.DAY_OF_MONTH).toString()}." +
                        "${(calendar.get(Calendar.MONTH)+1).toString()}." +
                        "${calendar.get(Calendar.YEAR).toString()}")
            }
            calendar.add(Calendar.DAY_OF_YEAR,1)
        }
    }
    fun toSettings(v: View) {
        val i= Intent(this,SettingsActivity::class.java)
        startActivity(i)
    }
    fun plusOne(v: View) {
        var y = date.text.toString()
        date.setText((y.toInt()+1).toString())
        fullDates()
    }
    fun minusOne(v: View) {
        var y = date.text.toString()
        date.setText((y.toInt()-1).toString())
        fullDates()
    }
}
