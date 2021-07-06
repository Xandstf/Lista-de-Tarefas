package br.edu.ifsp.scl.ads.pdm.contatoskt.model

interface TarefaDao {
    fun createTarefa(tarefa: Tarefa)
    fun readTarefa(titulo: String): Tarefa
    fun readTarefas(): ArrayList<Tarefa>
    fun updateTarefa(tarefa: Tarefa)
    fun finishTarefa(tarefa: Tarefa, index: Int)
    fun deleteTarefa(titulo: String)
}