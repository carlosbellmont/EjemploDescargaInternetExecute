package com.cbellmont.ejemplodescargainternet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.cbellmont.ejemplodescargainternet.databinding.ActivityMainBinding
import kotlinx.coroutines.*

interface MainActivityInterface {
    suspend fun onFilmsReceived(listFilms : List<Film>)
}

// IMPORTANT: Passing the activity to a the receiver is not a good practice, it may cause issues
// with the activity-s lifecycle. We are doing it just to keep the focus on the target of this example
class MainActivity : AppCompatActivity(), MainActivityInterface {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.IO){
            GetAllFilms.send(this@MainActivity)
        }
    }

    override suspend fun onFilmsReceived(listFilms : List<Film>) {
        withContext(Dispatchers.Main){
            binding.tvFilms.text = ""
            listFilms.forEach {
                binding.tvFilms.append(it.toString())
            }
        }

    }
}