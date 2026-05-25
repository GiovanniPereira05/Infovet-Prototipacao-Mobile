package com.example.infovet

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class DownloadDataset : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_download_dataset)

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
    }
}