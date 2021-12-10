package com.example.films.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.films.R
import com.example.films.databinding.ActivityMainBinding
import com.example.films.ui.listFilms.ListFilmsFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            refreshActivity()
        }
    }

    override fun onStart() {
        super.onStart()

        binding.apply {
            refreshButton.setOnClickListener { refreshActivity() }
        }
    }

    private fun refreshActivity() {
        binding.apply {
            if (checkForInternet(this@MainActivity)) {
                noInternetInfo.visibility = View.INVISIBLE
                filmInformation.visibility = View.VISIBLE

                supportFragmentManager
                    .beginTransaction()
                    .replace(filmInformation.id, ListFilmsFragment())
                    /*.replace(filmInformation.id, FilmInformationFragment())*/
                    .commit()
            } else {
                filmInformation.visibility = View.INVISIBLE
                Glide.with(this@MainActivity)
                    .load(R.drawable.no_internet)
                    .into(internetError)
            }
        }
    }

    /*override fun onFilmSelected(film: FilmsDataClasses) {
        binding.apply {

            *//*val detailsFragment =
                FilmInformationFragment.newInstance(FilmsDataClasses)*//*
            supportFragmentManager
                .beginTransaction()
                .replace(filmInformation.id, FilmInformationFragment())
                .addToBackStack(null)
                .commit()
        }
    }*/

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}