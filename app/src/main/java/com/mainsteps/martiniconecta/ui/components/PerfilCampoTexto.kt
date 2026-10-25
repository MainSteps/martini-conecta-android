package com.mainsteps.martiniconecta.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp

@Composable
fun PerfilCampoTexto(
    label: String,
    valor: String,
    error: String?,
    onValorChange: (String) -> Unit,
    onFocusLost: () -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    textoExito: String = "Correcto"
) {
    var fueTocado by rememberSaveable { mutableStateOf(false) }
    var teniaFoco by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        modifier = modifier.onFocusChanged { estadoFoco ->
            if (estadoFoco.isFocused) {
                teniaFoco = true
            } else if (teniaFoco) {
                fueTocado = true
                onFocusLost()
            }
        },
        label = { Text(text = label) },
        placeholder = placeholder?.let { hint ->
            { Text(text = hint) }
        },
        isError = error != null,
        supportingText = when {
            error != null -> {
                {
                    MensajeCampoPerfil(
                        texto = error,
                        esError = true
                    )
                }
            }
            fueTocado -> {
                {
                    MensajeCampoPerfil(
                        texto = textoExito,
                        esError = false
                    )
                }
            }
            else -> null
        },
        keyboardOptions = keyboardOptions,
        singleLine = true
    )
}

@Composable
private fun MensajeCampoPerfil(
    texto: String,
    esError: Boolean
) {
    val color = if (esError) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary
    }
    val icono = if (esError) {
        Icons.Filled.Warning
    } else {
        Icons.Filled.Check
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = texto,
            style = MaterialTheme.typography.bodySmall,
            color = color
        )
    }
}
