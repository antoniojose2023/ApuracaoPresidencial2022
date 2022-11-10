package br.com.antoniojoseuchoa.apuracaopresidencial2022.util

import java.text.SimpleDateFormat
import java.util.Calendar

abstract class Util {
    companion object{
        fun getDateAtual(): String{
            val dateFull = Calendar.getInstance().time
            val dateFormart = SimpleDateFormat("MM/dd/YYYY")
            return dateFormart.format( dateFull )
        }

        fun getTimeAtual(): String{
            val dateFull = Calendar.getInstance().time
            val dateFormart = SimpleDateFormat("HH:mm:ss")
            return dateFormart.format( dateFull )
        }
    }
}