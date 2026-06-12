package com.example.infovet

import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Inicial : AppCompatActivity() {

    private lateinit var helper: SQLiteOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicial)

        // 1. Referência do TextView que criamos no XML
        val textViewUsuarios = findViewById<TextView>(R.id.textViewUsuarios)

        // Usamos o StringBuilder para montar o texto da tela de forma eficiente
        val textoParaTela = StringBuilder()
        textoParaTela.append("📋 LISTA DE USUÁRIOS\n\n")

        helper = DatabaseHelper(this)
        val db = helper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)

        if (cursor.moveToFirst()) {
            do {
                // Extração dos dados
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOME))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EMAIL))
                val telefone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TELEFONE))
                val senha = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SENHA))
                val salt = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SALT))

                // --- JEITO 1: LOGCAT (Para depuração) ---
                Log.e("MeusUsuarios", "Usuário: $nome Email: $email Telefone: $telefone Senha: $senha Salt: $salt")

                // --- JEITO 2: TELA (Para o usuário ver) ---
                textoParaTela.append("Nome: $nome\n")
                textoParaTela.append("Email: $email\n")
                textoParaTela.append("Tel: $telefone\n")
                textoParaTela.append("Senha: $senha\n")
                textoParaTela.append("Salt: $salt\n")
                textoParaTela.append("--------------------------------\n")

            } while (cursor.moveToNext())
        } else {
            textoParaTela.append("Nenhum registro encontrado no banco.")
        }
        cursor.close()

        // 2. Seta o texto acumulado no TextView da tela
        textViewUsuarios.text = textoParaTela.toString()
    }
}