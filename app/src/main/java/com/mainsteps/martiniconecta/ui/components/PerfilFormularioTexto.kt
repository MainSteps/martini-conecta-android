package com.mainsteps.martiniconecta.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mainsteps.martiniconecta.model.PerfilUiState

@Composable
fun PerfilFormularioTexto(
    uiState: PerfilUiState,
    onNombreChange: (String) -> Unit,
    onNombreFocusLost: () -> Unit,
    onCorreoChange: (String) -> Unit,
    onCorreoFocusLost: () -> Unit,
    onTelefonoChange: (String) -> Unit,
    onTelefonoFocusLost: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PerfilCampoTexto(
            label = "Nombre",
            valor = uiState.nombre.valor,
            error = uiState.nombre.error,
            onValorChange = onNombreChange,
            onFocusLost = onNombreFocusLost,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier.fillMaxWidth()
        )

        PerfilCampoTexto(
            label = "Correo",
            valor = uiState.correo.valor,
            error = uiState.correo.error,
            onValorChange = onCorreoChange,
            onFocusLost = onCorreoFocusLost,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier.fillMaxWidth()
        )

        PerfilCampoTexto(
            label = "Teléfono (opcional)",
            valor = uiState.telefono.valor,
            error = uiState.telefono.error,
            onValorChange = onTelefonoChange,
            onFocusLost = onTelefonoFocusLost,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            placeholder = "+56 9 XXXX XXXX",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
