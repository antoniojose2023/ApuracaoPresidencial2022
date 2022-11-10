package br.com.antoniojoseuchoa.apuracaopresidencial2022.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.antoniojoseuchoa.apuracaopresidencial2022.data.RepositoryElection
import br.com.antoniojoseuchoa.apuracaopresidencial2022.domain.Cand
import br.com.antoniojoseuchoa.apuracaopresidencial2022.domain.Eleicao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelMain: ViewModel() {
    private val _candidatos: MutableLiveData<States> = MutableLiveData()
    val candidatos: LiveData<States> = _candidatos

    fun getDataEleicao(){
            _candidatos.value = States.Loader
            RepositoryElection.retrofit.getElection().enqueue(object: Callback<Eleicao>{
                override fun onResponse(call: Call<Eleicao>, response: Response<Eleicao>) {
                    if(response.body() != null){
                        _candidatos.value = States.OnSucess(response.body()!!)
                    }

                }

                override fun onFailure(call: Call<Eleicao>, t: Throwable) {
                    _candidatos.value = States.Erro(t.message.toString())
                }
            })
    }

    sealed class States{
        object Loader: States()
        class Erro(val erro: String): States()
        class OnSucess(val eleicao: Eleicao): States()
    }

}