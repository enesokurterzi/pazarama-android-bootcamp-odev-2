package com.example.odev_2.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.odev_2.KategoriRepository
import com.example.odev_2.Parca
import com.example.odev_2.ParcaRepository
import com.example.odev_2.ui.theme.Odev_2Theme

class MainActivity : ComponentActivity() {
    private lateinit var kategoriRepository: KategoriRepository
    private lateinit var parcaRepository: ParcaRepository
    private lateinit var kategorikParcalar: MutableList<Parca>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        kategoriRepository = KategoriRepository(this)
        parcaRepository = ParcaRepository(this)

        sharedPreferences = this.getSharedPreferences("firstTime", Context.MODE_PRIVATE)
        val firstTime = sharedPreferences.getBoolean("firstTimeValue", true)

        if (firstTime) {
            kategoriRepository.kategorileriOlustur()
            parcaRepository.parcalariOlustur()
            with(sharedPreferences.edit()) {
                putBoolean("firstTimeValue", false)
                apply()
            }
        }

        setContent {
            Odev_2Theme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    kategorikParcalar = parcaRepository.parcalarByKategoriID(1)
                    Navigation(kategoriRepository, parcaRepository, kategorikParcalar)
                }
                // A surface container using the 'background' color from the theme
            }
        }
    }
}

