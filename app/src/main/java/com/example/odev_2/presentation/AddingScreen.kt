package com.example.odev_2.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.odev_2.KategoriRepository
import com.example.odev_2.Parca
import com.example.odev_2.ParcaRepository
import kotlinx.coroutines.Delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddingScreen(
    navController: NavHostController,
    kategoriRepository: KategoriRepository,
    parcaRepository: ParcaRepository
) {
    val context = LocalContext.current

    var adi by remember {
        mutableStateOf("")
    }

    var isErrorinAdi by remember {
        mutableStateOf(false)
    }

    var stokAdedi by remember {
        mutableStateOf("")
    }

    var isErrorinStokAdedi by remember {
        mutableStateOf(false)
    }

    var fiyat by remember {
        mutableStateOf("")
    }

    var isErrorinFiyat by remember {
        mutableStateOf(false)
    }

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var secilenKategori by remember {
        mutableStateOf("")
    }

    var secilenKategoriID by remember {
        mutableStateOf(0)
    }

    var isErrorinSecilenKategori by remember {
        mutableStateOf(false)
    }

    val kategoriler = kategoriRepository.getKategoriler()

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.9f),
            value = adi,
            onValueChange = {
                adi = it
                isErrorinAdi = it.isEmpty()
            },
            label = { Text(text = "İsim") },
            singleLine = true,
            supportingText = {
                if (isErrorinAdi) {
                    Text(text = "İsim boş olamaz!")
                }
            },
            isError = isErrorinAdi
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.9f),
            value = stokAdedi,
            onValueChange = {
                stokAdedi = it
                isErrorinFiyat = it.isEmpty()
            },
            label = { Text(text = "Stok adedi") },
            singleLine = true,
            supportingText = {
                if (isErrorinStokAdedi) {
                    Text(text = "Stok adedi boş olamaz!")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isErrorinFiyat
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.9f),
            value = fiyat,
            onValueChange = {
                fiyat = it
                isErrorinStokAdedi = it.isEmpty()
            },
            label = { Text(text = "Fiyat") },
            singleLine = true,
            supportingText = {
                if (isErrorinFiyat) {
                    Text(text = "Fiyat boş olamaz!")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isErrorinStokAdedi
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(0.6f),
                expanded = isExpanded,
                onExpandedChange = { isExpanded = !isExpanded }
            ) {
                OutlinedTextField(
                    value = secilenKategori,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = {
                        Text(text = "Kategori Seçiniz")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor(),
                    supportingText = {
                        if (isErrorinSecilenKategori) {
                            Text(text = "Kategori belirtmelisiniz!")
                        }
                    },
                    isError = isErrorinSecilenKategori
                )
                ExposedDropdownMenu(
                    modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer),
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    kategoriler.forEach {

                        DropdownMenuItem(
                            text =
                            {
                                Text(text = it.Aciklama)
                            },
                            onClick = {
                                secilenKategori = it.Aciklama
                                secilenKategoriID = it.K_ID
                                isErrorinSecilenKategori = false

                                isExpanded = false
                            }
                        )
                    }
                }
            }

            Column {
                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = {
                        if (adi.isEmpty()) {
                            isErrorinAdi = true
                        }
                        if (stokAdedi.isEmpty()) {
                            isErrorinStokAdedi = true
                        }
                        if (fiyat.isEmpty()) {
                            isErrorinFiyat = true
                        }
                        if (secilenKategori.isEmpty()) {
                            isErrorinSecilenKategori = true
                        }
                        if (!isErrorinAdi && !isErrorinStokAdedi
                            && !isErrorinFiyat && !isErrorinSecilenKategori
                        ) {
                            val eklenenParca =
                                Parca(
                                    P_ID = -1,
                                    Kategori_ID = secilenKategoriID,
                                    Adi = adi,
                                    StokAdedi = stokAdedi.toInt(),
                                    Fiyati = fiyat.toLong()
                                )
                            parcaRepository.parcaEkle(eklenenParca)
                            Toast.makeText(context,"Ekleme başarılı.",Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    }
                ) {
                    Text(
                        text = "Ekle"
                    )
                }
            }

        }


    }

}

