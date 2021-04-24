package com.example.MovieApp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {
    lateinit var bundle: Bundle
    lateinit var imageView: ImageView
    lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentHost) as NavHostFragment
        navController = navHostFragment.navController

        imageView = findViewById(R.id.main_menu)

        main_menu.setOnClickListener {
            navController.popBackStack(R.id.dashboard_menu, false)
            changeIcon(main_menu, R.drawable.ic_home_active)
            changeIcon(menu_tiketing, R.drawable.ic_tiket)
            changeIcon(menu_profile, R.drawable.ic_profile)

        }
        menu_tiketing.setOnClickListener {
            if (navController.currentDestination?.id != R.id.tiketing) {
                navController.navigate(R.id.tiketing)
            }
            changeIcon(main_menu, R.drawable.ic_home)
            changeIcon(menu_tiketing, R.drawable.ic_tiket_active)
            changeIcon(menu_profile, R.drawable.ic_profile)
        }

        menu_profile.setOnClickListener {
            if (navController.currentDestination?.id != R.id.profile) {
                navController.navigate(R.id.profile)
            }
            changeIcon(main_menu, R.drawable.ic_home)
            changeIcon(menu_tiketing, R.drawable.ic_tiket)
            changeIcon(menu_profile, R.drawable.ic_profile_active)
        }
    }

    fun changeIcon(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.dashboard_menu || navController.currentDestination?.id == R.id.tiketing || navController.currentDestination?.id == R.id.profile ){
            finishAffinity()
        }else{
            super.onBackPressed()
        }
    }
}