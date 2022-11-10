package br.com.antoniojoseuchoa.apuracaopresidencial2022.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class RepositoryElection {
    companion object{

        private val url_base = "https://resultados.tse.jus.br/"
        //private val url_base = "https://resultados.tse.jus.br/oficial/ele2022/544/dados-simplificados/br/br-c0001-e000544-r.json"

        val retrofit = Retrofit.Builder()
            .baseUrl( url_base )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create( ApiTSE::class.java )
    }
}