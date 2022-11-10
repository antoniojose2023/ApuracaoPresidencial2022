package br.com.antoniojoseuchoa.apuracaopresidencial2022.view


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import android.widget.Toast
import androidx.activity.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.antoniojoseuchoa.apuracaopresidencial2022.R
import br.com.antoniojoseuchoa.apuracaopresidencial2022.databinding.ActivityMainBinding
import br.com.antoniojoseuchoa.apuracaopresidencial2022.domain.Cand
import br.com.antoniojoseuchoa.apuracaopresidencial2022.domain.Eleicao
import br.com.antoniojoseuchoa.apuracaopresidencial2022.util.Util
import br.com.antoniojoseuchoa.apuracaopresidencial2022.viewmodel.ViewModelMain


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModelMain by viewModels<ViewModelMain>()
    private lateinit var eleicao: Eleicao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar!!.title = "Apuração 2022"
        searchData()

        binding.swipeRefress.setOnRefreshListener(this)

    }

    override fun onResume() {
        viewModelMain.getDataEleicao()
        searchData()
        super.onResume()
    }

    fun searchData(){
        viewModelMain.candidatos.observe(this){ states ->
            when(states){
                ViewModelMain.States.Loader -> {

                }
                is ViewModelMain.States.Erro -> {
                    Toast.makeText(this, "Erro ${states.erro}", Toast.LENGTH_LONG).show()
                }
                is ViewModelMain.States.OnSucess -> {
                    if(states.eleicao.cand.isNotEmpty()){
                        fillDataFirstCand1(states.eleicao.cand)
                        fillDataFirstCand2(states.eleicao.cand)
                    }

                    eleicao = states.eleicao
                    fillDataElection(eleicao)

                }
            }
        }
    }


    fun fillDataFirstCand1(list: List<Cand>){
        list[0].also { cand ->
              binding.tvNome1.text = cand.nm
              binding.tvPartido1.text = "${cand.cc.substring(0, 2)} - ${cand.n}"
              binding.tvNumeroVotosValidos1.text = cand.vap


              if(cand.n.equals("13")){
                  binding.profileImage1.setImageResource(R.drawable.lula)
              }else{
                  binding.profileImage1.setImageResource(R.drawable.bolsonaro)
              }

              configProgressCand1(cand)



        }
    }

    fun configProgressCand1(cand: Cand){
            val a = cand.pvap.substring(0,2)
            val b = a.toInt()

                    var i = 0
                    while (i <= b){
                        binding.progressPorcentagem1.setProgress(i, true)
                        i++
                    }


           binding.tvPorcentagem1.text = "${cand.pvap}% "
    }

    fun fillDataFirstCand2(list: List<Cand>){
        list[1].also { cand ->
            binding.tvNome2.text = cand.nm
            binding.tvPartido2.text = "${cand.cc.substring(0, 2)} - ${cand.n}"
            binding.tvNumeroVotosValidos2.text = cand.vap


            if(cand.n.equals("13")){
                binding.profileImage2.setImageResource(R.drawable.lula)
            }else{
                binding.profileImage2.setImageResource(R.drawable.bolsonaro)
            }
            configProgressCand2(cand)
        }
    }

    fun configProgressCand2(cand: Cand){
        val a = cand.pvap.substring(0,2)
        val b = a.toInt()

        var i = 0
        while (i <= b){
            binding.progressPorcentagem2.setProgress(i, true)
            i++
        }

        binding.tvPorcentagem2.text = "${cand.pvap}% "
    }

    fun fillDataElection(eleicao: Eleicao){
        binding.tvTurno.text = "${eleicao.t}° turno"
        binding.tvEleicoes.text= "Eleição - ${eleicao.ele}"
        binding.tvSesoesApuradas.text = eleicao.st
        binding.tvNumeroTotalSecoes.text = eleicao.s
        binding.tvVotosApurados.text = eleicao.tv

        fillGraphData(eleicao)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_atualizar){
               viewModelMain.getDataEleicao()
               searchData()
               binding.swipeRefress.isRefreshing = false
               showMessage("Atualizado com sucesso . . .")
        }
        return super.onOptionsItemSelected(item)
    }

    fun fillGraphData(eleicao: Eleicao){
           binding.circularProgressBar.apply {
                progress = eleicao.st.toFloat()
                progressMax = eleicao.s.toFloat()
                setProgressWithAnimation(eleicao.st.toFloat(), 1000)
                progressBarColor = Color.parseColor("#4D814F")
                backgroundProgressBarColor = Color.LTGRAY
           }

           binding.tvDataHora.text = "${Util.getTimeAtual()} ${Util.getDateAtual()}"
    }

    override fun onRefresh() {
        viewModelMain.getDataEleicao()
        searchData()
        binding.swipeRefress.isRefreshing = false
        showMessage("Atualizado com sucesso . . .")
    }

    fun showMessage(msg: String){
        Toast.makeText(this, "${msg}", Toast.LENGTH_LONG).show()
    }
}