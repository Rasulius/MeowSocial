package com.rossgramm.rossapp

import android.os.Bundle
import android.view.Menu
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.rossgramm.rossapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.home_fragment_nav) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBar(navController, appBarConfiguration)
        setupBottomNavMenu(navController)
        // прячем и показываем меню, если у нас вид на всю страницу
        hideAndUnHideNavigationMenu(navController)

    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav?.setupWithNavController(navController)
    }

    private fun hideAndUnHideNavigationMenu(navController: NavController) {
        // Для блока комментариев скрваем навигацию. Включаем когда возвращаемся обратно
        val bottomNav = findViewById<BottomNavigationView>(R.id.nav_view)
        navController.addOnDestinationChangedListener { TODO , destination, _ ->
            // TODO Узнать как называется фрагмент комментария.
            // по названию не срабатывает? внимание быдлкод!!!
            if (destination.id == 2131296621) {
                bottomNav.visibility = View.GONE
            } else {
                bottomNav.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val retValue = super.onCreateOptionsMenu(menu)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        if (navigationView == null) {
            menuInflater.inflate(R.menu.bottom_nav_menu, menu)
            return true
        }
        return retValue
    }

}