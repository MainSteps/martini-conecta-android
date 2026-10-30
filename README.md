# Martini Conecta

Prototipo móvil académico desarrollado para el caso Master Martini en **DSY1105 — Desarrollo de Aplicaciones Móviles**, sección 003V, grupo 15.

## Propósito

App de contenidos técnicos para clientes y emprendedores de panadería, pastelería, heladería y gastronomía de Master Martini Chile (filial de Unigrà). Lleva del contenido al producto Martini y del producto a una conversación con ventas por WhatsApp. Diseñada para usuarios mayores de 50, con bajo manejo digital o dificultades visuales: simple, accesible y disponible sin conexión.

## Funcionalidades implementadas

- Perfil local con división, tamaño de negocio, intereses y teléfono opcional.
- Persistencia del perfil, preferencias, favoritos y solicitudes mediante Room.
- Catálogo local de 12 recetas, organizado por división y disponible sin conexión.
- Inicio personalizado según la división guardada en el perfil.
- Exploración por título y división, con detalle completo de cada receta.
- Favoritos sin duplicados que sobreviven al reinicio de la aplicación.
- Escalado de ingredientes a ×½, ×1 y ×2.
- Formulario validado «Quiero que me contacten», con datos precargados desde el perfil.
- Foto de perfil desde galería o cámara mediante Activity Result APIs y FileProvider.
- Acciones para compartir una receta y consultar a ventas mediante intents.
- Accesibilidad: tipografía escalable, preferencia de letra grande, contraste, áreas táctiles amplias y semántica para TalkBack.

## Tecnologías

- Kotlin y Jetpack Compose con Material 3.
- Navigation Compose.
- ViewModel, StateFlow y corrutinas.
- Room con migraciones para persistencia local.
- Activity Result APIs, Photo Picker, cámara y FileProvider.
- Coil para mostrar la foto de perfil.
- JUnit y kotlinx-coroutines-test para pruebas unitarias.

## Requisitos

- Android Studio con JDK 17.
- Android SDK Platform 37 y Build Tools 36.0.0.
- Dispositivo o emulador con Android 7.0 (API 24) o superior.

El proyecto incluye Gradle Wrapper 9.6.0, por lo que no requiere una instalación global de Gradle.

## Ejecución

1. Clona el repositorio y ábrelo en Android Studio.
2. Espera la sincronización de Gradle.
3. Selecciona un dispositivo o emulador con API 24 o superior.
4. Ejecuta la configuración `app`.

También puedes verificar el proyecto desde una terminal en la raíz:

```powershell
.\gradlew.bat assembleDebug testDebugUnitTest lintDebug
```

En macOS o Linux:

```bash
./gradlew assembleDebug testDebugUnitTest lintDebug
```

La APK de depuración se genera en `app/build/outputs/apk/debug/app-debug.apk`.

## Verificación final

Antes del congelamiento se comprueba:

- onboarding → Inicio → Explorar → Detalle → Favoritos;
- persistencia del perfil y de favoritos después de reiniciar;
- edición del perfil, galería, cámara y formulario de contacto;
- compartir y consultar a ventas mediante intents;
- fuente del sistema al máximo y lectura de acciones con TalkBack;
- `assembleDebug`, `testDebugUnitTest`, `lintDebug` y `git diff --check`.

Los datos ingresados durante las pruebas deben ser ficticios.

## Equipo

- Diego Alonso Pazos Atarama — [@MainSteps](https://github.com/MainSteps)
- Matías Urrejola — [@MatiasU-24](https://github.com/MatiasU-24)

## Gestión del proyecto (GitHub Projects en lugar de Trello)

La planificación vive en el [GitHub Project del equipo](https://github.com/users/MainSteps/projects/2), que reemplaza a Trello (la pauta acepta "Trello o similar").

- Columnas: **Por hacer → En curso → Hecho**.
- Cada tarjeta es una issue con responsable y label de nivel (`nivel 1: pauta`, `nivel 2: diferenciador`, `nivel 3: extra`).
- Flujo: **issue → rama `feat/<n>-…` → PR con `Closes #<n>` y revisión cruzada → Squash and merge → Hecho**.
- Cada integrante commitea desde su propia cuenta; no hay commits directos a `main`.

Detalle en [CONTRIBUTING.md](CONTRIBUTING.md) y contexto completo en [docs/contexto/contexto.md](docs/contexto/contexto.md).

## Contexto

Este repositorio corresponde a un prototipo académico basado en un desafío empresarial real. No implica que Master Martini haya contratado, aprobado o utilizado la aplicación. Marca, productos y WhatsApp se usan en modo demo con el aviso "Prototipo académico, no oficial".
