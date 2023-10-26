package com.example.odev_2.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.odev_2.KategoriRepository
import com.example.odev_2.Parca
import com.example.odev_2.ParcaRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    kategoriRepository: KategoriRepository,
    parcaRepository: ParcaRepository,
    kategorikParcalar: MutableList<Parca>
) {
    val kategoriler = kategoriRepository.getKategoriler()

    val secilenKategori = remember { mutableStateOf(kategoriler[0]) }

    var isExpanded by remember {
        mutableStateOf(false)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = { AddingButton(navController) }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .padding(paddingValue),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(0.4f),
                expanded = isExpanded,
                onExpandedChange = { isExpanded = !isExpanded }
            ) {
                TextField(
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    value = secilenKategori.value.Aciklama,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
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
                                secilenKategori.value = it

                                kategorikParcalar.clear()
                                kategorikParcalar
                                    .addAll(parcaRepository.parcalarByKategoriID(secilenKategori.value.K_ID))

                                isExpanded = false
                            })
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            ParcaListesiGoster(lst = kategorikParcalar)
        }

    }
}

@Composable
fun ParcaGoster(p: Parca) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text(text = p.Adi, style = MaterialTheme.typography.headlineMedium)

            Text(text = "Stok Adedi: ${p.StokAdedi}", style = MaterialTheme.typography.bodyMedium)

            Text(text = "Fiyatı: ${p.Fiyati} TL", style = MaterialTheme.typography.bodyMedium)

        }

    }
}

@Composable
fun ParcaListesiGoster(lst: List<Parca>) {
    LazyColumn(
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = true
    )
    {
        this.items(lst)
        {
            ParcaGoster(p = it)
        }
    }
}

@Composable
fun AddingButton(navController: NavHostController) {
    IconButton(
        modifier = Modifier.size(50.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        onClick = {
            navController.navigate(Screen.AddingScreen.route)
        }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
        )
    }
}
