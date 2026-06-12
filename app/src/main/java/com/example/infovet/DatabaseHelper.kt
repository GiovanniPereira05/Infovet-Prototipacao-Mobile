package com.example.infovet

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

object TableSession {
    const val TABLE_NAME  = "Sessao"
    const val COL_ID = "Id"
    const val COL_EMAIL = "Email"
    const val COL_TOKEN = "Token"
    const val COL_EXPIRATION = "Expiration"
}


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MeuBanco.db"
        private const val DATABASE_VERSION = 5
        const val TABLE_NAME = "Usuarios"
        const val COL_ID = "id"
        const val COL_NOME = "nome"
        const val COL_EMAIL = "email"

        const val COL_TELEFONE = "telefone"
        const val COL_SENHA = "senha"
        const val COL_SALT = "salt"
    }

    // Chamado na primeira vez que o banco é acessado
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NOME TEXT, $COL_EMAIL TEXT, $COL_TELEFONE TEXT, $COL_SENHA TEXT, $COL_SALT TEXT)"
        db?.execSQL(createTable)
        val createTableSessao = "CREATE TABLE IF NOT EXISTS ${TableSession.TABLE_NAME} (${TableSession.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${TableSession.COL_TOKEN} TEXT, ${TableSession.COL_EMAIL} TEXT,  ${TableSession.COL_EXPIRATION} INTEGER)"
        db?.execSQL(createTableSessao)
    }

    // Chamado quando a versão do banco muda
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addUsuario(usuario: CadastroModel): Long {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COL_NOME, usuario.usuario)
            put(COL_EMAIL, usuario.email)
            put(COL_TELEFONE, usuario.telefone)
            put(COL_SENHA, usuario.senha)
            put(COL_SALT, usuario.salt)
        }
        val newRowId = db.insert(TABLE_NAME, null, values)

        return newRowId
    }

    fun addSessao(sessao: SessionModel): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(TableSession.COL_EMAIL, sessao.email)
            put(TableSession.COL_TOKEN, sessao.token)
            put(TableSession.COL_EXPIRATION, sessao.expericao)
        }
        val newRowId = db.insert(TableSession.TABLE_NAME, null, values)
        return newRowId
    }

    fun checkSessao(token: String, tempoAtual: Long): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${TableSession.TABLE_NAME} WHERE ${TableSession.COL_TOKEN} = ? AND ${TableSession.COL_EXPIRATION} > ?",
            arrayOf(token, tempoAtual.toString())
        )
        if (cursor.moveToFirst()) {
            cursor.close()
            return true
        } else {
            cursor.close()
            return false
        }
    }

    fun verificarEmailExiste(emailDigitado: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $COL_EMAIL = ?",
            arrayOf(emailDigitado)
        )

        val existe = cursor.count > 0

        cursor.close()

        return existe
    }

    fun buscarUsuarioPorEmail(emailDigitado: String): CadastroModel? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $COL_EMAIL = ?",
            arrayOf(emailDigitado)
        )

        var usuarioEncontrado: CadastroModel? = null

        // Se o cursor achar algo (moveToFirst for true), extraímos os dados
        if (cursor.moveToFirst()) {
            val nome = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOME))
            val emailBanco = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL))
            val telefone = cursor.getString(cursor.getColumnIndexOrThrow(COL_TELEFONE))
            val senhaHash = cursor.getString(cursor.getColumnIndexOrThrow(COL_SENHA))

            // ATENÇÃO: Lembre-se de ter a constante COL_SALT no topo do seu DatabaseHelper!
            val salt = cursor.getString(cursor.getColumnIndexOrThrow(COL_SALT)) // Troque pelo nome da sua coluna de salt

            // Monta o objeto com os dados que vieram do banco
            usuarioEncontrado = CadastroModel(nome, emailBanco, telefone, senhaHash, salt)
        }

        cursor.close()
        return usuarioEncontrado
    }
}