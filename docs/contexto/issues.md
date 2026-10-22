# Issues y pauta

Mapa leído en GitHub el 22 de octubre de 2026. Dieciséis issues abiertas (#3 a #18) y dos cerradas (#1 y #2). El indicador principal es el de la rúbrica del Encargo (Parcial 2). Varias issues también empujan otro indicador; la tabla marca el que la propia issue declara y, si hace falta, el secundario.

No hay hitos (milestones). Los estados del tablero no se pudieron leer: no apareció un GitHub Project vinculado al repositorio.

## Abiertas

| Issue | Responsable | Indicador | Qué tiene que quedar en el repo |
|---|---|---|---|
| [#3](https://github.com/MainSteps/martini-conecta-android/issues/3) Tema Material 3 y componentes | MatiasU-24 | IE 2.1.1 (15 %) | Paleta, tipografía, formas, tarjetas, encabezados y estados. Contraste y área táctil. Sin material corporativo no autorizado. |
| [#4](https://github.com/MainSteps/martini-conecta-android/issues/4) Navegación y esqueletos | MainSteps | IE 2.1.1 (15 %) | Rutas centralizadas, `NavHost`, onboarding si no hay perfil, atrás coherente. PR [#20](https://github.com/MainSteps/martini-conecta-android/pull/20) abierto, sin aprobación. |
| [#5](https://github.com/MainSteps/martini-conecta-android/issues/5) Catálogo local | MatiasU-24 | IE 2.3.1 (15 %) | Modelo con id, título, descripción, categoría, formato, dificultad, duración, imagen y etiquetas. 9 a 12 contenidos ficticios detrás de un repositorio. |
| [#6](https://github.com/MainSteps/martini-conecta-android/issues/6) Onboarding y formulario | MainSteps | IE 2.1.2 (15 %) | Nombre, correo, tipo, tamaño e intereses. Al menos un interés. Error visible por campo. |
| [#7](https://github.com/MainSteps/martini-conecta-android/issues/7) Validación y estado | MainSteps | IE 2.2.1 (10 %) | Reglas fuera de los Composables. Un estado y eventos claros. Guardar solo con datos válidos. |
| [#8](https://github.com/MainSteps/martini-conecta-android/issues/8) Inicio personalizado | MainSteps | IE 2.2.1 (10 %), también IE 2.1.1 | La issue lo ata al uso del estado y al valor del MVP. Saludo con el perfil, destacados según intereses, estado vacío y sin reglas duplicadas en la pantalla. |
| [#9](https://github.com/MainSteps/martini-conecta-android/issues/9) Exploración, filtros y detalle | MatiasU-24 | IE 2.1.1 (15 %) | Búsqueda local, filtro por categoría o formato, vacío comprensible, detalle con los metadatos y navegación desde la lista. |
| [#10](https://github.com/MainSteps/martini-conecta-android/issues/10) Room | MatiasU-24 | IE 2.3.1 (15 %) | Entidades, DAO, base y repositorios. Sin favoritos duplicados. Perfil y favoritos vivos tras reiniciar. |
| [#11](https://github.com/MainSteps/martini-conecta-android/issues/11) Favoritos en detalle y biblioteca | MatiasU-24 | IE 2.3.1 (15 %), también estado | Alta y baja desde el detalle, lista persistida, estado vacío que explica cómo guardar. |
| [#12](https://github.com/MainSteps/martini-conecta-android/issues/12) Cámara y galería | MatiasU-24 | IE 2.4.1 (15 %) | Galería con Activity Result. Cámara con URI de FileProvider. Cancelar no cierra. Permiso denegado se explica. La imagen se ve y se conserva. |
| [#13](https://github.com/MainSteps/martini-conecta-android/issues/13) Adaptabilidad | MatiasU-24 | IE 2.1.1 (15 %) | Window Size Classes. Sin recortes. Exploración y detalle usan el ancho. La navegación no cambia de lógica. |
| [#14](https://github.com/MainSteps/martini-conecta-android/issues/14) Animaciones | MainSteps | IE 2.2.2 (10 %) | Feedback de favorito, validación o estado. No bloquean. Siguen siendo legibles si el sistema reduce el movimiento. |
| [#15](https://github.com/MainSteps/martini-conecta-android/issues/15) Accesibilidad y usabilidad | MainSteps | IE 2.1.1 (15 %) | Etiquetas, contraste, áreas táctiles, errores que no dependen solo del color, flujo completable sin instructivo. |
| [#16](https://github.com/MainSteps/martini-conecta-android/issues/16) Compilación en pull requests | MainSteps | IE 2.3.2 (20 %) | Workflow en cada PR hacia `main`, visible como check. El README no duplica el estado del tablero. No se fusiona en rojo. Hoy no hay workflows. |
| [#17](https://github.com/MainSteps/martini-conecta-android/issues/17) Documentar y congelar | MainSteps y MatiasU-24 | IE 2.3.2 (20 %) y condiciones de entrega | README de producto, evidencia, SHA entregado, paquete de AVA y ningún cambio después del congelamiento. |
| [#18](https://github.com/MainSteps/martini-conecta-android/issues/18) Pruebas cruzadas | MainSteps y MatiasU-24 | IE 2.3.2 (20 %) y estabilidad del resto | Recorrido completo. Persistencia, cámara, galería y adaptabilidad comprobadas. Cada uno prueba el código del otro. Los fallos vuelven como issues. |

## Cerradas

| Issue | Responsable | Indicador | Nota |
|---|---|---|---|
| [#1](https://github.com/MainSteps/martini-conecta-android/issues/1) Proyecto base | MainSteps | IE 2.3.1 (15 %), base de ejecución | Cerrada por el PR #19. Paquetes `model`, `repository`, `viewmodel` y `ui/theme`. El saludo de plantilla sigue en `main`. |
| [#2](https://github.com/MainSteps/martini-conecta-android/issues/2) GitHub Project | MatiasU-24 | IE 2.3.2 (20 %) | Cerrada. La URL del Project no se pudo confirmar por API. |

## Pull requests

| PR | Estado | Issue |
|---|---|---|
| [#19](https://github.com/MainSteps/martini-conecta-android/pull/19) Estructura inicial | Fusionado | #1 |
| [#20](https://github.com/MainSteps/martini-conecta-android/pull/20) Navegación y esqueletos | Abierto, revisión pendiente | #4 |

Cuando una issue se cierre o cambie de responsable, actualiza esta tabla en el mismo PR. No reasignes desde aquí.
