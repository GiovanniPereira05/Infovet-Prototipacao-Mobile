package com.example.infovet


import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

object DrawerHelper {

    fun setupDrawer(
        activity: AppCompatActivity,
        drawerLayout: DrawerLayout,
        navView: NavigationView,
        toolbar: Toolbar
    ) {

        activity.setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            activity,
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
                    Toast.makeText(activity, "Login", Toast.LENGTH_SHORT).show()
                    activity.startActivity(
                        Intent(activity, infovetlogin::class.java)
                    )
                }

                R.id.nav_cadastro -> {
                    Toast.makeText(activity, "Cadastro", Toast.LENGTH_SHORT).show()
                    activity.startActivity(
                        Intent(activity, MainActivity::class.java)
                    )
                }

                R.id.nav_tela_inicial -> {
                    Toast.makeText(activity, "Tela Inicial", Toast.LENGTH_SHORT).show()
                    activity.startActivity(
                        Intent(activity, TelaInicial::class.java)
                    )
                }

                R.id.nav_datasets -> {
                    Toast.makeText(activity, "Dataset", Toast.LENGTH_SHORT).show()
                    activity.startActivity(
                        Intent(activity, DestaquesActivity::class.java)
                    )
                }

                R.id.nav_modelos -> {
                    Toast.makeText(activity, "Modelos", Toast.LENGTH_SHORT).show()
                    activity.startActivity(
                        Intent(activity, ModelosActivity::class.java)
                    )
                }
            }

            drawerLayout.closeDrawers()
            true
        }
    }
}