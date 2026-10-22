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
| Navigation Compose 2.10.2 | Solo en el PR #20, sin fusionar | Rutas y `NavHost` |
| ViewModel y StateFlow | Acordados, sin dependencia de ViewModel en `main` | Estado |
| Room | Acordado en la issue #10, sin dependencia | Perfil y favoritos |
| Window Size Classes | Acordadas en la issue #13, sin dependencia | Compacto, mediano, expandido |
| Activity Result APIs y FileProvider | APIs de plataforma; la integración es la issue #12 | Cámara y galería |

Identificadores: namespace y `applicationId` `com.mainsteps.martiniconecta`. El nombre del proyecto Gradle y el `app_name` de recursos siguen siendo `MartiniConecta_Grupo15` (plantilla). El producto se llama Martini Conecta. No hay workflows en `.github/workflows/`; la compilación en pull requests es la issue #16.

Quedan fuera Hilt, Koin, Retrofit, Firebase, DataStore y cualquier cliente de red. Las versiones nuevas se declaran en el catálogo, dentro de la issue que las necesita.

## Qué hay en `main`

Módulo `:app`. `MainActivity` muestra el `Greeting` de la plantilla dentro de un `Scaffold`, con `enableEdgeToEdge()` y el tema `MartiniConecta_Grupo15Theme`. Ese tema usa la paleta púrpura por defecto y `dynamicColor = true` en Android 12 o superior.

Paquetes con código: `ui/theme/` (`Color.kt`, `Theme.kt`, `Type.kt`). Paquetes creados y vacíos (solo `.gitkeep`): `model/`, `repository/`, `viewmodel/`. Pruebas de ejemplo en `test/` y `androidTest/`. El manifiesto no declara permisos ni `FileProvider`.

## Qué trae el PR #20 (abierto)

Rama `feat/4-navegacion-esqueletos`. No está en `main`.

- `navigation/Routes.kt`: clase sellada con las rutas de texto.
- `navigation/AppNavigation.kt`: `NavHost` y destino inicial según `hasProfile`.
- `navigation/AppBottomBar.kt`: barra de Inicio, Explorar, Favoritos y Perfil.
- `ui/screens/`: onboarding, explorar, detalle y, en `SimpleScreens.kt`, inicio, favoritos y perfil. El contenido visible es un `ScreenPlaceholder`.
- `MainActivity` llama a `AppNavigation(hasProfile = false)`.
- Dependencia `androidx.navigation:navigation-compose:2.10.2` en el catálogo.

Hasta que ese PR se fusione, esas clases no son parte de la rama principal.

## Capas acordadas

| Capa | Responsabilidad | Dónde vive |
|---|---|---|
| UI | Screens y componentes Material 3. Dibujan estado y emiten eventos. | `ui/screens`, `ui/components` (este último aún no existe), `ui/theme` |
| ViewModel | `StateFlow`, validación y acciones. | `viewmodel/` |
| Modelo | Perfil, contenido, categorías y estado de pantalla. | `model/` |
| Repositorio | Catálogo local y operaciones de Room, separados de la UI. | `repository/` (ya creado en la raíz del paquete) |
| Datos | Entidades, DAO y base para perfil y favoritos. | `data/local/`, a crear en la issue #10 |
| Navegación | Rutas y un solo `NavHost`. | `navigation/` |
| Nativos | Contratos de actividad, FileProvider y URI. | Flujo de perfil, más manifiesto y `res/xml` |
| Adaptabilidad | Window Size Class hacia la UI. | `ui/` o un helper en `ui/utils/` si hace falta |

El informe del grupo proponía también `data/repository/`. El repositorio real ya tiene `repository/`. No dupliques la capa: los repositorios se quedan en `repository/` y Room va a `data/local/`. No muevas paquetes existentes en un PR que no trate de eso.

## Decisiones

- MVVM del curso: la pantalla no contiene las reglas.
- Un perfil local y favoritos en Room. El catálogo de demostración se sirve por repositorio, sin red.
- Navegación centralizada en la clase sellada `Routes` del PR #20. Las pantallas no reciben el `NavController`.
- Cámara y galería solo en el perfil, con Activity Result y FileProvider.
- Tres tamaños de ventana, no una comprobación de anchos repartida por la UI.
- GitHub público, issues, ramas y pull requests. La pauta pide Trello o una herramienta similar. El informe del grupo registra que el profesor indicó que Trello no es obligatorio, y el equipo eligió GitHub Projects. La API del repositorio no mostró un Project vinculado; el detalle está en [flujo-de-trabajo.md](flujo-de-trabajo.md).
- Código pensado para explicarse: un estado por flujo, operaciones de persistencia con nombres directos y la navegación en un solo archivo de rutas.
