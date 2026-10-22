# Martini Conecta — guía para agentes

Prototipo académico Android del grupo 15 (DSY1105 Desarrollo de Aplicaciones Móviles, Duoc UC, sección 003V) para el caso Master Martini Chile SPA. La app organiza contenidos técnicos ficticios —recetas, videos y cursos— para emprendedores y profesionales gastronómicos, con perfil local, favoritos e interfaz simple para distintos niveles de manejo digital.

El profesor permite usar IA en el desarrollo y el equipo está de acuerdo. El código debe quedar explicable por Diego y por Matías sin el asistente: nombres claros, una responsabilidad por capa y flujos localizables.

## Stack y arquitectura

Kotlin, Jetpack Compose y Material 3, con MVVM. En `main` el módulo `app` (`com.mainsteps.martiniconecta`, minSdk 24, targetSdk 37) tiene el tema de plantilla y los paquetes vacíos `model/`, `repository/` y `viewmodel/`. Room, ViewModel, StateFlow, cámara, galería y compartir con Intent están acordados en issues, pero no hay dependencias ni código de eso en `main`.

El PR [#20](https://github.com/MainSteps/martini-conecta-android/pull/20) (abierto, sin fusionar) agrega Navigation Compose, la clase sellada `Routes` y esqueletos de Onboarding, Inicio, Explorar, Detalle, Favoritos y Perfil. No lo trates como código de `main` ni abras un segundo grafo de rutas.

## Reglas de trabajo

- Una issue, una rama (`feat/<n>-…`, `fix/`, `docs/`, `test/`, `chore/`) y un pull request. Revisa la otra persona. Cada integrante commitea desde su cuenta (`MainSteps`, `MatiasU-24`).
- No amplíes el stack: sin login, backend, nube, analítica ni material corporativo no autorizado. Solo datos ficticios.
- La validación y las reglas de negocio viven en el ViewModel. El Composable pinta estado y emite eventos.
- Congelamiento la noche del 30 de octubre de 2026. Subida del encargo el 31 de octubre de 2026 a las 15:00 (hora de Santiago). Después de esa entrega no se modifica el repositorio: un cambio, aunque sea mínimo, puede calificarse con 1,0.

## Contexto

Antes de implementar, lee el índice en [`docs/contexto/README.md`](docs/contexto/README.md). Ahí están el caso, el alcance, la arquitectura, la pauta del Encargo, el flujo de trabajo y el mapa de issues.
