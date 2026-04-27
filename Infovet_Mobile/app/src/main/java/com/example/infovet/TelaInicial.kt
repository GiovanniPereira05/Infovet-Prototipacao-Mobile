package com.example.infovet
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle

class TelaInicial : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ⚠️ troca se o nome do XML for diferente
        setContentView(R.layout.activity_tela_inicial)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_login -> {
                    Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, infovetlogin::class.java)
                    startActivity(intent)
                }

                R.id.nav_cadastro -> {
                    Toast.makeText(this, "Cadastro", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_tela_inicial -> {
                    Toast.makeText(this, "Tela Inicial", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, TelaInicial::class.java)
                    startActivity(intent)
                }
                R.id.nav_datasets -> {
                    Toast.makeText(this, "Dataset", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Datasets::class.java)
                    startActivity(intent)
                }
            }

            drawerLayout.closeDrawers()
            true
        }
    }
}