package com.mainsteps.martiniconecta.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.Interes
import com.mainsteps.martiniconecta.model.PerfilUiState
import com.mainsteps.martiniconecta.model.TamanoNegocio

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PerfilFormularioSelecciones(
    uiState: PerfilUiState,
    onDivisionChange: (Division) -> Unit,
    onTamanoChange: (TamanoNegocio) -> Unit,
    onInteresToggle: (Interes) -> Unit,
    modifier: Modifier = Modifier
) {
    var divisionTocada by rememberSaveable { mutableStateOf(false) }
    var tamanoTocada by rememberSaveable { mutableStateOf(false) }
    var interesesTocados by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PerfilSeccionTitulo(titulo = "División")
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Division.entries.forEach { division ->
                    OptionButton(
                        label = division.etiqueta,
                        selected = uiState.division.valor == division,
                        onClick = {
                            divisionTocada = true
                            onDivisionChange(division)
                        }
                    )
                }
            }
            val mensajeDivision = uiState.division.error
            PerfilValidacionAnimada(visible = divisionTocada && mensajeDivision != null) {
                if (mensajeDivision != null) {
                    PerfilMensajeValidacion(
                        texto = mensajeDivision,
                        estilo = PerfilMensajeEstilo.Error
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PerfilSeccionTitulo(titulo = "Tamaño de tu negocio")
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TamanoNegocio.entries.forEach { tamano ->
                    OptionButton(
                        label = tamano.etiqueta,
                        selected = uiState.tamano.valor == tamano,
                        onClick = {
                            tamanoTocada = true
                            onTamanoChange(tamano)
                        }
                    )
                }
            }
            val mensajeTamano = uiState.tamano.error
            PerfilValidacionAnimada(visible = tamanoTocada && mensajeTamano != null) {
                if (mensajeTamano != null) {
                    PerfilMensajeValidacion(
                        texto = mensajeTamano,
                        estilo = PerfilMensajeEstilo.Error
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PerfilSeccionTitulo(titulo = "Intereses")
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Interes.entries.forEach { interes ->
                    FilterChip(
                        selected = interes in uiState.intereses.seleccionados,
                        onClick = {
                            interesesTocados = true
                            onInteresToggle(interes)
                        },
                        label = {
                            Text(
                                text = interes.etiqueta,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        modifier = Modifier.heightIn(min = 48.dp)
                    )
                }
            }
            val mensajeIntereses = uiState.intereses.error
            PerfilValidacionAnimada(visible = interesesTocados && mensajeIntereses != null) {
                if (mensajeIntereses != null) {
                    PerfilMensajeValidacion(
                        texto = mensajeIntereses,
                        estilo = PerfilMensajeEstilo.Error
                    )
                }
            }
        }
    }
}
