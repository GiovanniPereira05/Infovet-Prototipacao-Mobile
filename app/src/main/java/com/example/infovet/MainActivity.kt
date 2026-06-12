package com.example.infovet


import LoginViewModel
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    private lateinit var buttonCadastrar: Button

    private lateinit var textViewUsuario: TextView
    private lateinit var editTextUsuario: EditText
    private lateinit var textViewEmail: TextView
    private lateinit var editTextEmail: EditText

    private lateinit var textViewTelefone: TextView
    private lateinit var editTextTelefone: EditText
    private lateinit var textViewSenha: TextView
    private lateinit var editTextSenha: EditText
    private lateinit var helper: DatabaseHelper
    private lateinit var sessionManager: SessionManager
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var viewModel: LoginViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)



        sessionManager = SessionManager(this)
        databaseHelper = DatabaseHelper(this) // Instancia seu helper de banco de dados

        // Passa ambos para o construtor da ViewModel
        viewModel = LoginViewModel(sessionManager, databaseHelper)

        // Executa a checagem inteligente
        viewModel.verificarSessaoValida(
            onSessaoValida = {
                Toast.makeText(this, "Login feito com sucesso.", Toast.LENGTH_LONG).show()
                val intent = Intent(this, TelaInicial::class.java)
                startActivity(intent)

            },
            onSessaoExpirada = {
                // O token expirou ou não existe. Força o login.
                Toast.makeText(this, "Sessão expirada. Faça login novamente.", Toast.LENGTH_LONG).show()
                val intent = Intent(this, infovetlogin::class.java)
                startActivity(intent)

            })







        buttonCadastrar = findViewById(R.id.buttonCadastrar)
        buttonCadastrar.setOnClickListener {
            val intent = Intent(this, infovetlogin::class.java)
            startActivity(intent)
        }

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

        helper = DatabaseHelper(this)



        textViewUsuario = findViewById(R.id.textViewUsuario)
        editTextUsuario = findViewById(R.id.editTextUsuario)

        textViewEmail = findViewById(R.id.textViewEmail)
        editTextEmail = findViewById(R.id.editTextEmail)

        textViewTelefone = findViewById(R.id.textViewTelefone)
        editTextTelefone = findViewById(R.id.editTextTelefone)

        textViewSenha = findViewById(R.id.textViewSenha)
        editTextSenha = findViewById(R.id.editTextSenha)

        buttonCadastrar = findViewById(R.id.buttonCadastrar)




        buttonCadastrar.setOnClickListener {

            if(helper.verificarEmailExiste(editTextEmail.text.toString())){
                editTextEmail.error = "Este e-mail já está cadastrado!"
                Toast.makeText(this, "E-mail já em uso. Tente fazer login.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

//           Verifica se o campo usuario está vazio
            if (editTextUsuario.text.toString().trim().isEmpty()){
                editTextUsuario.error = "O nome de usuário é obrigatório!"
                editTextUsuario.requestFocus()
                return@setOnClickListener
            }

            if(!isSenhaForte(editTextSenha.text.toString())){
                editTextSenha.error = "A senha deve ter no mínimo 8 caracteres, 1 letra maiúscula, 1 minúscula, 1 número e 1 caractere especial."
                Toast.makeText(this, "Senha muito fraca!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(!isEmailValido(editTextEmail.text.toString())){
                editTextEmail.error = "Email invalido"
                Toast.makeText(this, "Email invalido!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val salt = gerarSalt()
            val senha_hash = gerarHashSenha(editTextSenha.text.toString() + salt + Config.pepper)


            val usuario = CadastroModel(editTextUsuario.text.toString(), editTextEmail.text.toString(), editTextTelefone.text.toString(), senha_hash, salt)
            val row_id = helper.addUsuario(usuario)



        }


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}