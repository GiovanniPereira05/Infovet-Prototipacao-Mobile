package com.example.infovet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class infovetlogin : AppCompatActivity() {
    private lateinit var buttonLogin: Button
    private lateinit var editTextEmailLogin: EditText
    private lateinit var editTextSenhaLogin: EditText

    private lateinit var helper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_infovetlogin)
        helper = DatabaseHelper(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)

        val drawerLayout: DrawerLayout =
            findViewById(R.id.drawer_layout)

        val navView: NavigationView =
            findViewById(R.id.nav_view)

        DrawerHelper.setupDrawer(
            this,
            drawerLayout,
            navView,
            toolbar
        )
        supportActionBar?.setDisplayShowTitleEnabled(false)

        buttonLogin = findViewById(R.id.buttonEntrar)

        editTextEmailLogin = findViewById(R.id.editTextEmailLogin)
        editTextSenhaLogin = findViewById(R.id.editTextSenhaLogin)

        buttonLogin.setOnClickListener {
            val usuario =  helper.buscarUsuarioPorEmail(editTextEmailLogin.text.toString())
            if (usuario != null){
                val hash = gerarHashSenha(editTextSenhaLogin.text.toString() + usuario.salt + Config.pepper)
                if(hash == usuario.senha){
                    Toast.makeText(this, "Login efetuado com sucesso! Bem-vindo, ${usuario.usuario}", Toast.LENGTH_SHORT).show()

                    val expiracao = getExpiracao()
                    val token = gerarToken()

                    val sessao = SessionModel(usuario.email, token, expiracao)
                    helper.addSessao(sessao)




                    val intent = Intent(this, Inicial::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "E-mail ou senha incorretos.", Toast.LENGTH_LONG).show()
                }
            }else {
                Toast.makeText(this, "E-mail ou senha incorretos.", Toast.LENGTH_LONG).show()
            }
        }
        }

    }