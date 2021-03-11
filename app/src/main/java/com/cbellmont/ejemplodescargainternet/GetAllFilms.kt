package com.cbellmont.ejemplodescargainternet

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject


class GetAllFilms {
    companion object {
        suspend fun send() : List<Film> {


            val client = OkHttpClient()
            val url = "https://swapi.dev/api/films/"
            val request = Request.Builder()
                .url(url)
                .build()
            val call = client.newCall(request)

            return withContext(Dispatchers.IO) {
                val response = call.execute()
                response.body?.string()?.let {
                    Log.w("GetAllFilms", it)
                    val jsonObject = JSONObject(it)

                    val results = jsonObject.optJSONArray("results")
                    results?.let {
                        Log.w("GetAllFilms", results.toString())
                        val gson = Gson()

                        val itemType = object : TypeToken<List<Film>>() {}.type

                        val list = gson.fromJson<List<Film>>(results.toString(), itemType)
                        return@withContext list
                    }
                }
                return@withContext emptyList()
            }
        }
    }
}