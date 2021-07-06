package br.edu.ifsp.scl.ads.pdm.contatoskt.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.edu.ifsp.scl.ads.pdm.contatoskt.AutenticacaoFirebase
import br.edu.ifsp.scl.ads.pdm.contatoskt.databinding.ActivityRecuperarSenhaBinding

class RecuperarSenhaActivity : AppCompatActivity() {
    private lateinit var activityRecuperarSenhaActivity: ActivityRecuperarSenhaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecuperarSenhaActivity = ActivityRecuperarSenhaBinding.inflate(layoutInflater)
        setContentView(activityRecuperarSenhaActivity.root)
    }

    fun onClick(view: View){
        if (view == activityRecuperarSenhaActivity.enviarEmailBt){
            val email = activityRecuperarSenhaActivity.emailRecuperacaoSenhaEt.text.toString()
            AutenticacaoFirebase.firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener {
                Toast.makeText(this, "Email de recuperação enviado para $email", Toast.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener{
                Toast.makeText(this, "Falha ao enviar email de recuperação", Toast.LENGTH_SHORT).show()
            }
        }
    }
}