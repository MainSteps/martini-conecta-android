# Arquitectura

## Stack

| Pieza | Estado en el repo | Uso |
|---|---|---|
| Kotlin 2.2.10 | En `main` (`gradle/libs.versions.toml`) | Lenguaje |
| Android Gradle Plugin 9.4.1 | En `main` | Build |
| Jetpack Compose (BOM 2026.02.01) y Material 3 | En `main` | UI |
| `activity-compose` 1.8.0, `lifecycle-runtime-ktx` 2.6.1 | En `main` | Actividad |
| Java 11 | En `main` | Compilación |
| minSdk 24, targetSdk 37, compileSdk 37 | En `main` | Dispositivos |
| Navigation Compose 2.10.2 | En `main` (PR #20 fusionado) | Rutas y `NavHost` |
| ViewModel y StateFlow | Acordados, sin dependencia de ViewModel en `main` | Estado |
| Room | Acordado en las issues #10 (perfil) y #27 (favoritos), sin dependencia | Perfil y favoritos |
| Activity Result APIs y FileProvider | APIs de plataforma; la integración son las issues #12 (galería) y #28 (cámara) | Cámara y galería |

Identificadores: namespace y `applicationId` `com.mainsteps.martiniconecta`. El nombre del proyecto Gradle y el `app_name` de recursos siguen siendo `MartiniConecta_Grupo15` (plantilla). El producto se llama Martini Conecta. El CI de compilación en pull requests está en `.github/workflows/android-ci.yml` (#16).

Quedan fuera Hilt, Koin, Retrofit, Firebase, DataStore y cualquier cliente de red. La preferencia "letra grande" (Nivel 3) se guarda en la entidad de perfil de Room, no en DataStore. Las versiones nuevas se declaran en el catálogo, dentro de la issue que las necesita.

## Qué hay en `main`

Módulo `:app`. `MainActivity` muestra el `Greeting` de la plantilla dentro de un `Scaffold`, con `enableEdgeToEdge()` y el tema `MartiniConecta_Grupo15Theme`. Ese tema usa la paleta púrpura por defecto y `dynamicColor = true` en Android 12 o superior.

Paquetes con código: `ui/theme/` (`Color.kt`, `Theme.kt`, `Type.kt`). Paquetes creados y vacíos (solo `.gitkeep`): `model/`, `repository/`, `viewmodel/`. Pruebas de ejemplo en `test/` y `androidTest/`. El manifiesto no declara permisos ni `FileProvider`.

## Navegación (PR #20, fusionado)

`navigation/Routes.kt` (clase sellada), `navigation/AppNavigation.kt` (`NavHost`, destino inicial según `hasProfile`), `navigation/AppBottomBar.kt` (Inicio, Explorar, Favoritos, Perfil; la #3 le pone Material Icons + texto) y esqueletos en `ui/screens/`.

## Capas acordadas

| Capa | Responsabilidad | Dónde vive |
|---|---|---|
| UI | Screens y componentes Material 3. Dibujan estado y emiten eventos. | `ui/screens`, `ui/components` (este último aún no existe), `ui/theme` |
| ViewModel | `StateFlow`, validación y acciones. | `viewmodel/` |
| Modelo | Perfil, contenido (división, productos Martini, rinde, ingredientes), solicitud de contacto y estado de pantalla. | `model/` |
| Repositorio | Catálogo local y operaciones de Room, separados de la UI. | `repository/` (ya creado en la raíz del paquete) |
| Datos | Entidades, DAO y base para perfil, favoritos y solicitudes de contacto. | `data/local/`, a crear en #10, #27 y #24 |
| Navegación | Rutas y un solo `NavHost`. | `navigation/` |
| Nativos | Contratos de actividad, FileProvider y URI. | Flujo de perfil, más manifiesto y `res/xml` |

El informe del grupo proponía también `data/repository/`. El repositorio real ya tiene `repository/`. No dupliques la capa: los repositorios se quedan en `repository/` y Room va a `data/local/`. No muevas paquetes existentes en un PR que no trate de eso.

## Decisiones

- MVVM del curso: la pantalla no contiene las reglas.
- Un perfil local y favoritos en Room. El catálogo de demostración se sirve por repositorio, sin red.
- Navegación centralizada en la clase sellada `Routes` del PR #20. Las pantallas no reciben el `NavController`.
- Cámara y galería solo en el perfil, con Activity Result y FileProvider. Intents de compartir y WhatsApp solo en el detalle; el número de ventas en una sola constante.
- Accesibilidad resuelta en el tema (#3): tipografía en `sp`, contraste, 48 dp; las pantallas no fijan tamaños propios.
- GitHub público, issues, ramas y pull requests. La pauta pide Trello o una herramienta similar. El informe del grupo registra que el profesor indicó que Trello no es obligatorio, y el equipo eligió el [GitHub Project de MainSteps](https://github.com/users/MainSteps/projects/2). Ese Project es de usuario y no está vinculado al repositorio; conviene vincularlo. El detalle del flujo está en [flujo-de-trabajo.md](flujo-de-trabajo.md).
- Código pensado para explicarse: un estado por flujo, operaciones de persistencia con nombres directos y la navegación en un solo archivo de rutas.
