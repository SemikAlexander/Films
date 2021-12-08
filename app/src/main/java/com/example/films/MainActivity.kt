package com.example.films

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.films.databinding.ActivityMainBinding

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
                internetError.visibility = View.INVISIBLE
                refreshButton.visibility = View.INVISIBLE
                filmInformation.visibility = View.VISIBLE

                supportFragmentManager
                    .beginTransaction()
                    .replace(filmInformation.id, FilmInformationFragment())
                    .commit()
            } else {
                filmInformation.visibility = View.INVISIBLE
                Glide.with(this@MainActivity)
                    .load(R.drawable.no_internet)
                    .into(internetError)
            }
        }
    }

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