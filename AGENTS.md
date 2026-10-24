# Martini Conecta — guía para agentes

Prototipo académico Android del grupo 15 (DSY1105 Desarrollo de Aplicaciones Móviles, Duoc UC, sección 003V) para el caso Master Martini Chile SPA. La app lleva al usuario del contenido (recetas por división) al producto Martini y a una conversación con ventas por WhatsApp. Usuario objetivo: dueños de negocios gastronómicos, muchos mayores de 50, con bajo manejo digital o dificultades visuales.

El profesor permite usar IA en el desarrollo y el equipo está de acuerdo. El código debe quedar explicable por Diego y por Matías sin el asistente: nombres claros, una responsabilidad por capa y flujos localizables.

## Stack y arquitectura

Kotlin, Jetpack Compose y Material 3, con MVVM. `main` ya tiene Navigation Compose, la clase sellada `Routes`, esqueletos de pantallas y CI de compilación. Room, ViewModel, StateFlow, cámara, galería e Intents están en issues. No abras un segundo grafo de rutas.

## Reglas de trabajo

- Una issue, una rama (`feat/<n>-…`, `fix/`, `docs/`, `chore/`) y un pull request con CI en verde. Revisa la otra persona y se fusiona con squash. Cada integrante commitea desde su cuenta (`MainSteps`, `MatiasU-24`).
- No amplíes el stack: sin login, backend, nube, analítica ni DataStore. Datos personales solo ficticios.
- Marca Martini (colores, nombres de productos, WhatsApp público) solo en modo demo, con aviso "Prototipo académico, no oficial".
- Diseño: una acción principal por pantalla, cualquier receta en ≤ 3 toques, accesibilidad base en todo (texto en `sp`, cuerpo ≥ 16 sp, contraste ≥ 4.5:1, 48 dp, íconos con texto, `contentDescription`).
- Niveles: cada issue tiene label `nivel 1/2/3`. Nivel 1 cierra mar 27/10; Nivel 2 hasta jue 29/10 23:59; vie 30/10 Nivel 3 y prueba final; congelamiento vie 30/10 23:59. Lo no terminado se saca.
- La validación y las reglas de negocio viven en el ViewModel. El Composable pinta estado y emite eventos.
- Sábado 31 de octubre de 2026: solo trámites del AVA antes de las 15:00 (hora de Santiago). Desde el congelamiento no se modifica el repositorio: un cambio, aunque sea mínimo, puede calificarse con 1,0.

## Contexto

Antes de implementar, lee el índice en [`docs/contexto/README.md`](docs/contexto/README.md). Ahí están el caso, el alcance, la arquitectura, la pauta del Encargo, el flujo de trabajo y el mapa de issues.
