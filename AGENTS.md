# Martini Conecta — guía para agentes

App Android académica (DSY1105, grupo 15) para **Master Martini Chile**: lleva del contenido (recetas por división) al producto Martini y del producto a una conversación con ventas por WhatsApp. Usuario objetivo: dueños de negocios gastronómicos, muchos mayores de 50, con bajo manejo digital o dificultades visuales.

## Lee antes de trabajar
1. **[`docs/contexto/contexto.md`](docs/contexto/contexto.md)**: el único documento de contexto (caso, usuario, alcance por niveles, diseño y accesibilidad, arquitectura, pauta, calendario y flujo).
2. La issue asignada completa: cada una trae contexto, qué construir, archivos, criterios, fuera de alcance, dependencias y cómo probar.
3. Las reglas de `.cursor/rules/` (flujo de sesión, contexto, Kotlin/Compose, accesibilidad, navegación, Room, recursos nativos).

## Reglas clave
- Flujo de sesión: visión general → plan en to-dos → un to-do a la vez esperando "sigue" → preguntar antes de salir del alcance → `./gradlew assembleDebug` → un commit, push y PR con `Closes #<n>` y revisión de la otra persona → 3 preguntas de defensa.
- MVVM: la UI pinta estado; validación y reglas en el ViewModel; datos por repositorio; Room en `data/local/`.
- Accesibilidad en todo: `sp`, cuerpo ≥ 16 sp, contraste ≥ 4.5:1, 48 dp, íconos con texto, `contentDescription`.
- Sin comentarios en el código, sin trailer `Co-authored-by`, sin commits directos a `main`, sin force push.
- Congelamiento: viernes 30/10/2026 23:59 (Santiago). Después nadie pushea.
