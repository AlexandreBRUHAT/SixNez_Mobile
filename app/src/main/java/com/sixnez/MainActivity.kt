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
import androidx.lifecycle.Observer
import com.sixnez.model.User
import com.sixnez.service.setToken
import com.sixnez.viewmodel.MainViewModel


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private var viewModel : MainViewModel = MainViewModel()
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        var toggle : ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        viewModel.isConnected.observe(this, Observer { bool ->
            bool?.let {
                //connected
                navigationView.menu.findItem(R.id.nav_logout).setVisible(bool)
                navigationView.menu.findItem(R.id.nav_profile).setVisible(bool)
                navigationView.menu.findItem(R.id.nav_movies).setVisible(bool)
                navigationView.menu.findItem(R.id.nav_actors).setVisible(bool)

                //disconnected
                navigationView.menu.findItem(R.id.nav_login).setVisible(!bool)
                navigationView.menu.findItem(R.id.nav_register).setVisible(!bool)
            }
        })
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        }
        super.onBackPressed()

    }

    override fun onStart() {
        changeFragment(HomeFragment(), R.id.nav_home)
        super.onStart()
    }

    fun changeFragment(frag: Fragment, id : Int = 0) {
        var fragment = frag

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment,
            fragment
        ).commit()

        if (id != 0) {

            navigationView.setCheckedItem(id)
        }
    }

    fun connect(user: User?) {
        viewModel.connect(user)
    }

    fun disconnect() {
        viewModel.disconnect()
        changeFragment(HomeFragment(), R.id.nav_home)
        setToken("")
    }

    fun getUsername(): String? {
        return viewModel.user.value?.login
    }

    fun isConnected(): Boolean {
        return viewModel.isConnected.value?:false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_home -> changeFragment(HomeFragment())

            R.id.nav_movies -> changeFragment(FilmsFragment())

            R.id.nav_actors -> changeFragment(ActeursFragment())

            R.id.nav_login -> changeFragment(LoginFragment())

            R.id.nav_register -> changeFragment(RegisterFragment())

            R.id.nav_profile -> changeFragment(ProfileFragment())

            R.id.nav_about -> changeFragment(AboutFragment())

            R.id.nav_logout -> disconnect()
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
