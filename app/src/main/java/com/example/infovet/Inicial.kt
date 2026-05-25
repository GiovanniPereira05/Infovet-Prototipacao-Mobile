package com.example.infovet

import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Inicial : AppCompatActivity() {

    private lateinit var helper: SQLiteOpenHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicial)

        helper = DatabaseHelper(this)
        val db = helper.readableDatabase // Abre para leitura
        val cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)
        if (cursor.moveToFirst()) {
            do {
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOME))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EMAIL))
                val telefone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TELEFONE))
                val senha = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SENHA))
                val salt = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SALT))
                Log.e("MeusUsuarios", "Usuário: $nome Email: $email Telefone: $telefone Senha: $senha Salt: $salt")
            } while (cursor.moveToNext())
        }
        cursor.close()
    }
}