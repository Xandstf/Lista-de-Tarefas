package br.edu.ifsp.scl.ads.pdm.contatoskt.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.edu.ifsp.scl.ads.pdm.contatoskt.AutenticacaoFirebase
import br.edu.ifsp.scl.ads.pdm.contatoskt.databinding.ActivityCadastrarBinding

class CadastrarActivity : AppCompatActivity() {
    private lateinit var activityCadastrarActivity: ActivityCadastrarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCadastrarActivity = ActivityCadastrarBinding.inflate(layoutInflater)
        setContentView(activityCadastrarActivity.root)
    }

    fun onClick(view: View){
        if( view == activityCadastrarActivity.cadastrarBt){
            val email = activityCadastrarActivity.emailEt.text.toString()
            val senha = activityCadastrarActivity.senhaEt.text.toString()
            val repetirSenha = activityCadastrarActivity.repetirSenhaEt.text.toString()
            if(senha.equals(repetirSenha)){
                AutenticacaoFirebase.firebaseAuth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener { cadastro ->
                    if(cadastro.isSuccessful){
                        Toast.makeText(this, "$email cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this, "Falha ao cadastrar $email", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Senhas não são iguais", Toast.LENGTH_SHORT).show()
            }
        }
    }
}