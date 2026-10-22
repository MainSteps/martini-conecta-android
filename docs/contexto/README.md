# Pack de contexto

Documentación para que una persona o un agente de IA trabaje Martini Conecta alineado al caso, a la pauta del Encargo (Parcial 2) y al plan del grupo 15. No reemplaza el `README.md` de la raíz ni `CONTRIBUTING.md`: esos siguen siendo la puerta del repositorio y del flujo de contribución.

El código Android no se describe aquí como si ya estuviera terminado. Si este pack y el árbol de `main` se contradicen, manda el código que está en la rama que estás editando, y después se actualiza este directorio.

## Índice

| Documento | Para qué |
|---|---|
| [caso.md](caso.md) | Qué pide Master Martini y qué límites tiene el prototipo. |
| [alcance-y-pantallas.md](alcance-y-pantallas.md) | Funcionalidades, pantallas y recursos nativos del MVP. |
| [arquitectura.md](arquitectura.md) | Stack, paquetes que existen hoy y decisiones del equipo. |
| [pauta-encargo.md](pauta-encargo.md) | Indicadores del Parcial 2, pesos y cómo evidenciarlos. |
| [flujo-de-trabajo.md](flujo-de-trabajo.md) | Ramas, revisión cruzada, cuentas y dueños de cada bloque. |
| [issues.md](issues.md) | Issues abiertas y el indicador de pauta que cubren. |

La guía corta para agentes está en [`AGENTS.md`](../../AGENTS.md). Las reglas de Cursor están en `.cursor/rules/`.

## Cómo mantenerlo

Actualiza el pack en el mismo pull request que deje obsoleta una decisión, no en un commit suelto al final.

- Cambió un paquete, una ruta o una dependencia acordada: edita `arquitectura.md` y, si aplica, la regla en `.cursor/rules/`.
- Se abrió, cerró o reasignó una issue: edita `issues.md` y la tabla de dueños en `flujo-de-trabajo.md`. La asignación se copia del repositorio; este pack no la decide.
- Se fusionó el PR #20: marca en `arquitectura.md` que navegación y esqueletos ya están en `main`.
- No copies la pauta ni el calendario de la defensa (Parcial 3). La fecha de esa evaluación solo importa aquí como límite del congelamiento.
- No vuelvas a poner el 21 de octubre de 2026 como fecha de entrega. El informe del grupo trae ese calendario viejo. Vigente: congelamiento la noche del 30 de octubre de 2026, subida el 31 de octubre de 2026 a las 15:00 (hora de Santiago).

La fuente de verdad del trabajo diario siguen siendo las issues y GitHub Projects. Este pack guarda el contexto que las issues no repiten.
