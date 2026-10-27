package com.mainsteps.martiniconecta.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private const val DuracionValidacionMs = 300

@Composable
fun PerfilValidacionAnimada(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn(animationSpec = tween(DuracionValidacionMs)) +
            expandVertically(animationSpec = tween(DuracionValidacionMs)),
        exit = fadeOut(animationSpec = tween(DuracionValidacionMs)),
    ) {
        content()
    }
}
