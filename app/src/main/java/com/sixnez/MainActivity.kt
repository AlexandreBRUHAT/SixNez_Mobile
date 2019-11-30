package com.sixnez

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        var navigationView : NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        var toggle : ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onStart() {
        changeFragment(HomeFragment())
        super.onStart()
    }

    fun changeFragment(frag: Fragment) {
        var fragment = frag
        if (false) {
            //TODO si aucun user n'est connecté on renvoie vers la connexion
            fragment = HomeFragment()
        }

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment,
            fragment
        ).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_home -> changeFragment(HomeFragment())

            R.id.nav_movies -> changeFragment(FilmsFragment())

            R.id.nav_actors -> changeFragment(ActorsFragment())

            R.id.nav_login -> changeFragment(LoginFragment())

            R.id.nav_register -> changeFragment(RegisterFragment())

            R.id.nav_profile -> changeFragment(ProfileFragment())

            R.id.nav_about -> changeFragment(AboutFragment())
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
