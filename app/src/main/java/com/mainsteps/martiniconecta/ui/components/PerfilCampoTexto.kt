package com.mainsteps.martiniconecta.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged

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
    val mostrarError = fueTocado && error != null
    val mostrarExito = fueTocado && error == null

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
        isError = mostrarError,
        supportingText = if (fueTocado) {
            {
                PerfilValidacionAnimada(visible = mostrarError) {
                    if (error != null) {
                        PerfilMensajeValidacion(
                            texto = error,
                            estilo = PerfilMensajeEstilo.Error
                        )
                    }
                }
                PerfilValidacionAnimada(visible = mostrarExito) {
                    PerfilMensajeValidacion(
                        texto = textoExito,
                        estilo = PerfilMensajeEstilo.Exito
                    )
                }
            }
        } else {
            null
        },
        keyboardOptions = keyboardOptions,
        singleLine = true
    )
}
