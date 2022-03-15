 package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

 class MainActivity : AppCompatActivity() {
     // null init
     private var tvInput : TextView? = null
     // these stores if last input was numeric or dot
     private var lastNumeric : Boolean = false
     var lastDot : Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastDot = false
        lastNumeric=true

    }

     fun onClear(view :View){
         tvInput?.text=""
     }

     fun onDecimalPoint(view :View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
     }
     fun onOperator(view : View){
        // om begge statements er true(not empty), kan vi bruke it. Unngår kræsj
         tvInput?.text?.let{

             // sjekker om forrige tegn er et tall && ikke en operator
             if(lastNumeric && !isOperatorAdded(it.toString())){
                 tvInput?.append((view as Button).text)
                 lastNumeric = false
                 lastDot = false
             }
         }
     }
     // helpmethod til onOperator: håndterer operatorer. Value blir current text i tv
     private fun isOperatorAdded(value : String) : Boolean{
         return if(value.startsWith("-")){
             false
         } else{
             value.contains("/")
                     || value.contains("*")
                     || value.contains("+")
                     || value.contains("-")
         }
     }

     fun onEqual(view : View){
         if(lastNumeric){
             var tvValue = tvInput?.text.toString()
             var prefix = ""
             try{
                 // behander negative tall
                 if(tvValue.startsWith("-")){
                     prefix="-"
                     tvValue = tvValue.substring(1) // kutter første tegn, altså -
                 }
                 // - op
                 if(tvValue.contains("-")){
                     val splitValue = tvValue.split("-")

                     var one = splitValue[0]
                     var two = splitValue[1]

                     if(prefix.isNotEmpty()){
                         one = prefix + one
                     }
                     // kalkulerer resultat. Må konvertere fra String til double, så tilbake til string (tvInput krever en string)
                     // bruker en hm for å fjerne .0 cases
                     tvInput?.text = removeZeroAfterDot ((one.toDouble() - two.toDouble()).toString())

                 }
                 // + op
                 else if(tvValue.contains("+")){
                     val splitValue = tvValue.split("+")

                     var one = splitValue[0]
                     var two = splitValue[1]

                     if(prefix.isNotEmpty()){
                         one = prefix + one
                     }
                     tvInput?.text = removeZeroAfterDot ((one.toDouble() + two.toDouble()).toString())
                 }
                 // / op
                 else if(tvValue.contains("/")){
                     val splitValue = tvValue.split("/")

                     var one = splitValue[0]
                     var two = splitValue[1]

                     if(prefix.isNotEmpty()){
                         one = prefix + one
                     }
                     tvInput?.text = removeZeroAfterDot ((one.toDouble() / two.toDouble()).toString())
                 }
                 // * op
                 else if(tvValue.contains("*")){
                     val splitValue = tvValue.split("*")

                     var one = splitValue[0]
                     var two = splitValue[1]

                     if(prefix.isNotEmpty()){
                         one = prefix + one
                     }
                     tvInput?.text = removeZeroAfterDot ((one.toDouble() * two.toDouble()).toString())
                 }

             }catch(e: ArithmeticException){
                 e.printStackTrace() // printer på logcat
             }
         }
     }

     // help method for onEqual
     private fun removeZeroAfterDot(result: String) : String{
         var value = result
         // we only change it if it contains a .0
         if(result.contains(".0")){
             value = result.substring(0,result.length -2)
         }
        return value
     }

}