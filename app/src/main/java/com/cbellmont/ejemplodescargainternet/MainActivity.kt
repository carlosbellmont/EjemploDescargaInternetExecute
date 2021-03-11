package com.cbellmont.ejemplodescargainternet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cbellmont.ejemplodescargainternet.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.Main){
            onFilmsReceived(GetAllFilms.send())
        }
    }

    private fun onFilmsReceived(listFilms : List<Film>) {
        binding.tvFilms.text = ""
        listFilms.forEach {
            binding.tvFilms.append(it.toString())
        }
    }
}