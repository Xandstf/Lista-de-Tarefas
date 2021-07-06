package br.edu.ifsp.scl.ads.pdm.contatoskt.controller

import br.edu.ifsp.scl.ads.pdm.contatoskt.model.Tarefa
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.TarefaDao
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.TarefaFirebase
import br.edu.ifsp.scl.ads.pdm.contatoskt.view.MainActivity

class TarefaController(mainActivity: MainActivity) {
    val tarefaDao: TarefaDao
    init {
        tarefaDao = TarefaFirebase()
    }

    fun insereTarefa(tarefa: Tarefa) = tarefaDao.createTarefa(tarefa)
    fun buscaTarefa(titulo: String) = tarefaDao.readTarefa(titulo)
    fun buscaTarefas() = tarefaDao.readTarefas()
    fun atualizaTarefa(tarefa: Tarefa) = tarefaDao.updateTarefa(tarefa)
    fun concluiTarefa(tarefa: Tarefa, posicao: Int) = tarefaDao.finishTarefa(tarefa, posicao)
    fun removeTarefa(titulo: String) = tarefaDao.deleteTarefa(titulo)
}