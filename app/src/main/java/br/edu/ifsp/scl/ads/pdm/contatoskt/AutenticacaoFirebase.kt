package br.edu.ifsp.scl.ads.pdm.contatoskt

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

object AutenticacaoFirebase {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    //opções de sign in
    var googleSignInOptions: GoogleSignInOptions? = null
    //cliente que interage com a API google
    var googleSignInClient: GoogleSignInClient? = null
    //guarda as infos da conta autenticada
    var googleSignInAccount: GoogleSignInAccount? = null
}