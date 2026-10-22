# Issues y pauta

Mapa leído en GitHub el 22 de octubre de 2026. Reorganizado el mismo día según la pauta: dieciséis issues abiertas y cinco cerradas (#1, #2, #4, #13 y #15). Ninguna issue exige video; la evidencia es verificación en emulador y captura opcional. El indicador principal es el de la rúbrica del Encargo (Parcial 2). Varias issues también empujan otro indicador; la tabla marca el que la propia issue declara y, si hace falta, el secundario.

No hay hitos (milestones). El tablero es el [GitHub Project de MainSteps](https://github.com/users/MainSteps/projects/2). Es un Project de usuario y no está vinculado al repositorio; conviene vincularlo.

## Abiertas

| Issue | Responsable | Indicador | Qué tiene que quedar en el repo |
|---|---|---|---|
| [#3](https://github.com/MainSteps/martini-conecta-android/issues/3) Tema Material 3 y componentes | MatiasU-24 | IE 2.1.1 (15 %) | Paleta, tipografía, formas, tarjetas, encabezados y estados. Contraste y área táctil. Sin material corporativo no autorizado. |
| [#5](https://github.com/MainSteps/martini-conecta-android/issues/5) Catálogo local | MatiasU-24 | IE 2.3.1 (15 %) | Modelo con id, título, descripción, categoría, formato, dificultad, duración, imagen y etiquetas. 9 a 12 contenidos ficticios detrás de un repositorio. |
| [#6](https://github.com/MainSteps/martini-conecta-android/issues/6) Onboarding y formulario | MainSteps | IE 2.1.2 (15 %) | Nombre, correo, tipo, tamaño e intereses. Al menos un interés. Error visible por campo, con ícono de error o de éxito. |
| [#7](https://github.com/MainSteps/martini-conecta-android/issues/7) Validación y estado | MainSteps | IE 2.2.1 (10 %) | Reglas fuera de los Composables. Un estado y eventos claros. Guardar solo con datos válidos. |
| [#8](https://github.com/MainSteps/martini-conecta-android/issues/8) Inicio personalizado | MainSteps | IE 2.2.1 (10 %), también IE 2.1.1 | La issue lo ata al uso del estado y al valor del MVP. Saludo con el perfil, destacados según intereses, estado vacío y sin reglas duplicadas en la pantalla. |
| [#9](https://github.com/MainSteps/martini-conecta-android/issues/9) Exploración, filtros y detalle | MatiasU-24 | IE 2.1.1 (15 %) | Búsqueda local, filtro por categoría o formato, vacío comprensible, detalle con los metadatos y navegación desde la lista. |
| [#10](https://github.com/MainSteps/martini-conecta-android/issues/10) Room | MatiasU-24 | IE 2.3.1 (15 %) | Entidades, DAO, base y repositorios. Sin favoritos duplicados. Perfil y favoritos vivos tras reiniciar. |
| [#11](https://github.com/MainSteps/martini-conecta-android/issues/11) Favoritos en detalle y biblioteca | MatiasU-24 | IE 2.3.1 (15 %), también estado | Alta y baja desde el detalle, lista persistida, estado vacío que explica cómo guardar. |
| [#12](https://github.com/MainSteps/martini-conecta-android/issues/12) Cámara y galería | MatiasU-24 | IE 2.4.1 (15 %) | Galería con Activity Result. Cámara con URI de FileProvider. Cancelar no cierra. Permiso denegado se explica. La imagen se ve y se conserva. |
| [#14](https://github.com/MainSteps/martini-conecta-android/issues/14) Animaciones | MainSteps | IE 2.2.2 (10 %) | Feedback de favorito, validación o estado. No bloquean. Siguen siendo legibles si el sistema reduce el movimiento. |
| [#16](https://github.com/MainSteps/martini-conecta-android/issues/16) Compilación en pull requests | MainSteps | IE 2.3.2 (20 %) | Workflow en cada PR hacia `main`, visible como check. El README no duplica el estado del tablero. No se fusiona en rojo. Workflow en el PR [#22](https://github.com/MainSteps/martini-conecta-android/pull/22), en revisión. |
| [#17](https://github.com/MainSteps/martini-conecta-android/issues/17) Documentar y congelar | MainSteps y MatiasU-24 | IE 2.3.2 (20 %) y condiciones de entrega | README de producto, SHA entregado, repositorio público con acceso del docente, AVA grupal, AVA individual de cada uno y congelamiento total desde el 31/10/2026 a las 15:00. |
| [#18](https://github.com/MainSteps/martini-conecta-android/issues/18) Pruebas cruzadas | MainSteps y MatiasU-24 | IE 2.3.2 (20 %) y estabilidad del resto | Recorrido completo. Persistencia, cámara, galería y recursos nativos comprobados. Incluye los criterios de accesibilidad de la #15: etiquetas, contraste, áreas táctiles, errores que no dependen solo del color y flujo sin instructivo. Cada uno prueba el código del otro. Los fallos vuelven como issues. |
| [#23](https://github.com/MainSteps/martini-conecta-android/issues/23) Trámites AVA | MainSteps y MatiasU-24 | Condiciones de entrega | Repositorio público y docente con acceso, AVA grupal subido por una sola persona, AVA individual activado por cada uno y ningún cambio tras la entrega. |
| [#24](https://github.com/MainSteps/martini-conecta-android/issues/24) Segundo formulario: sugerir contenido | MainSteps | IE 2.1.2 (15 %), también IE 2.2.1 | Tema, categoría, formato y comentario. Error e ícono por campo, reglas fuera de los Composables, envío solo con datos válidos y guardado local. |
| [#25](https://github.com/MainSteps/martini-conecta-android/issues/25) Compartir con Intent | MatiasU-24 | IE 2.4.1 (15 %) | Botón Compartir en el detalle con `ACTION_SEND` y selector. Cancelar no cierra. Sin permisos extra. |

## Cerradas

| Issue | Responsable | Indicador | Nota |
|---|---|---|---|
| [#1](https://github.com/MainSteps/martini-conecta-android/issues/1) Proyecto base | MainSteps | IE 2.3.1 (15 %), base de ejecución | Cerrada por el PR #19. Paquetes `model`, `repository`, `viewmodel` y `ui/theme`. El saludo de plantilla sigue en `main`. |
| [#2](https://github.com/MainSteps/martini-conecta-android/issues/2) GitHub Project | MatiasU-24 | IE 2.3.2 (20 %) | Cerrada. Tablero: [Project de MainSteps](https://github.com/users/MainSteps/projects/2), a nivel de usuario y sin vincular al repositorio. |

## Pull requests

| PR | Estado | Issue |
|---|---|---|
| [#19](https://github.com/MainSteps/martini-conecta-android/pull/19) Estructura inicial | Fusionado | #1 |
| [#20](https://github.com/MainSteps/martini-conecta-android/pull/20) Navegación y esqueletos | Fusionado | #4 |
| [#22](https://github.com/MainSteps/martini-conecta-android/pull/22) CI de compilación | Abierto, revisión pendiente | #16 |

Cuando una issue se cierre o cambie de responsable, actualiza esta tabla en el mismo PR. No reasignes desde aquí.
