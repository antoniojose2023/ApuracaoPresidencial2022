package br.com.antoniojoseuchoa.apuracaopresidencial2022.data


import br.com.antoniojoseuchoa.apuracaopresidencial2022.domain.Eleicao
import retrofit2.Call

import retrofit2.http.GET

interface ApiTSE {
    @GET("oficial/ele2022/545/dados-simplificados/br/br-c0001-e000545-r.json")
    fun getElection(): Call<Eleicao>
}