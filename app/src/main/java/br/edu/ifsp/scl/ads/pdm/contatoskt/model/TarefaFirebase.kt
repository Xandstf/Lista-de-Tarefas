package br.edu.ifsp.scl.ads.pdm.contatoskt.model

import br.edu.ifsp.scl.ads.pdm.contatoskt.adapter.TarefasAdapter
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.TarefaFirebase.Constantes.LISTA_TAREFAS_DATABASE
import br.edu.ifsp.scl.ads.pdm.contatoskt.view.MainActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class TarefaFirebase: TarefaDao {
    object Constantes {
        val LISTA_TAREFAS_DATABASE = "listaTarefas"
    }
    private val tarefasListRtDb = Firebase.database.getReference(LISTA_TAREFAS_DATABASE)

    private val tarefasList: ArrayList<Tarefa> = ArrayList()
    init {
        tarefasListRtDb.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val novaTarefa: Tarefa = snapshot.getValue<Tarefa>()?:Tarefa()
                if (tarefasList.indexOfFirst { it.titulo.equals(novaTarefa.titulo) } == -1) {
                    tarefasList.add(novaTarefa)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val tarefaEditada: Tarefa = snapshot.getValue<Tarefa>()?:Tarefa()

                val posicao = tarefasList.indexOfFirst { it.titulo.equals(tarefaEditada.titulo) }
                tarefasList[posicao] = tarefaEditada
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val tarefaRemovida: Tarefa = snapshot.getValue<Tarefa>()?:Tarefa()
                tarefasList.remove(tarefaRemovida)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Não se aplica
            }

            override fun onCancelled(error: DatabaseError) {
                // Não se aplica
            }
        })
        tarefasListRtDb.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tarefasList.clear()
                for (item in snapshot.children) {
                    val tarefaBanco: Tarefa = item.getValue<Tarefa>()?:Tarefa()
                    tarefasList.add(tarefaBanco)
                }
                println(tarefasList)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    override fun createTarefa(tarefa: Tarefa) = createOuAtualizaTarefa(tarefa)

    override fun readTarefa(titulo: String): Tarefa = tarefasList[tarefasList.indexOfFirst { it.titulo.equals(titulo) }]

    override fun readTarefas(): ArrayList<Tarefa> = tarefasList

    override fun updateTarefa(tarefa: Tarefa) = createOuAtualizaTarefa(tarefa)

    override fun finishTarefa(tarefa: Tarefa, index: Int) {
        tarefa.isCompleta = true
        tarefasListRtDb.child(tarefa.titulo).setValue(tarefa)
    }

    override fun deleteTarefa(titulo: String) {
        tarefasListRtDb.child(titulo).removeValue()
    }

    private fun createOuAtualizaTarefa(tarefa: Tarefa) {
        tarefasListRtDb.child(tarefa.titulo).setValue(tarefa)
    }
}