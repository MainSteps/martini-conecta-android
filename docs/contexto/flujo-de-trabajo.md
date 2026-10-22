# Flujo de trabajo

Grupo 15. Propietario del repositorio: `MainSteps`. Colaborador: `MatiasU-24`. Rama por defecto: `main`. Visibilidad: pública.

La planificación vive en GitHub Issues y en el [GitHub Project de MainSteps](https://github.com/users/MainSteps/projects/2), con los estados Backlog, Por hacer, En curso, En revisión, Bloqueado y Terminado. La issue #2, asignada a Matías, está cerrada y dejó ese tablero listo. El Project es de usuario y no está vinculado al repositorio; conviene vincularlo para que se vea desde el repo. No abras otro tablero en Trello ni en paralelo.

## Reglas que no se negocian en un PR

1. Cada persona trabaja y hace commit desde su propia cuenta. Diego no entrega código como Matías, ni al revés. La rúbrica mira la participación de los dos.
2. Una issue por pull request. La rama sale de `main` actualizada.
3. Revisión cruzada: aprueba quien no escribió el cambio. Fusión con **Squash and merge**. Después se borra la rama y la issue pasa a Terminado solo si cumple la definición de terminado de `CONTRIBUTING.md`.
4. Sin commits directos a `main`. Así lo dicen el README y el informe. No se pudo leer la protección de rama: la API respondió 403.
5. Mensajes de commit con tipo y alcance técnico, en español (`feat:`, `fix:`, `docs:`, `test:`, `chore:`).
6. No se suben carpetas de build, secretos, `.idea/` ni datos personales.

El pull request del pack de contexto (rama `docs/contexto-ia`) es intencionalmente sin issue y con commit de Cursor Agent: es una herramienta de apoyo, independiente del alcance del proyecto. Las issues del proyecto sí siguen una issue, una rama y un pull request desde la cuenta del responsable.

## Ramas

| Prefijo | Cuándo |
|---|---|
| `feat/<numero>-<descripcion>` | Funcionalidad de esa issue |
| `fix/<numero>-<descripcion>` | Corrección |
| `docs/<numero>-<descripcion>` | Documentación atada a una issue |
| `test/<numero>-<descripcion>` | Pruebas |
| `chore/<numero>-<descripcion>` | Configuración o mantenimiento |

Ejemplo del propio repo: `feat/4-navegacion-esqueletos` para la issue #4. El número es el de la issue, no un correlativo inventado.

El pull request explica qué cambia y por qué, enlaza `Closes #<n>`, cuenta cómo se probó y adjunta captura o video si cambia la interfaz. Plantilla: `.github/PULL_REQUEST_TEMPLATE.md`.

## Dueños

La asignación se copió de las issues el 22 de octubre de 2026. Coincide con el informe del grupo. No la cambies desde la documentación.

### Diego Alonso Pazos Atarama (`MainSteps`)

| Issue | Tema |
|---|---|
| #1, cerrada | Proyecto Android base |
| #4, PR #20 abierto | Navegación y esqueletos |
| #6 | Onboarding y formulario de perfil |
| #7 | Validaciones y estado del perfil |
| #8 | Inicio personalizado |
| #14 | Animaciones y feedback |
| #15 | Accesibilidad y usabilidad |
| #16 | Compilación automática en pull requests |

En el informe, Diego concentra la base, la navegación, el perfil, la validación, las animaciones y el cierre de la integración. La issue #16 está asignada a él en GitHub aunque el informe describía la integración final como bloque compartido de cierre.

### Matías Urrejola (`MatiasU-24`)

| Issue | Tema |
|---|---|
| #2, cerrada | GitHub Project y trazabilidad |
| #3 | Tema Material 3 y componentes |
| #5 | Modelo y catálogo local |
| #9 | Exploración, búsqueda, filtros y detalle |
| #10 | Room de perfil y favoritos |
| #11 | Favoritos en detalle y biblioteca |
| #12 | Cámara y galería |
| #13 | Adaptabilidad compacta, mediana y expandida |

### Los dos

| Issue | Tema |
|---|---|
| #17 | Documentar, congelar y preparar la entrega del Parcial 2 |
| #18 | Integrar módulos y pruebas cruzadas |

`CODEOWNERS` lista a los dos en todo el árbol. Eso no sustituye la revisión del PR ni la asignación de la issue.

## Orden de dependencia

La navegación (#4) y el tema (#3) desbloquean las pantallas. El catálogo (#5) desbloquea inicio (#8) y exploración (#9). El formulario (#6) desbloquea la validación (#7). Room (#10) desbloquea favoritos integrados (#11) y necesita los modelos de perfil y contenido. Cámara y galería (#12) necesitan el perfil y la persistencia. Adaptabilidad (#13) y animaciones (#14) llegan cuando las pantallas ya existen. Accesibilidad (#15), compilación en CI (#16), pruebas cruzadas (#18) y entrega (#17) cierran.

Si una tarea se atrasa, se dice y se replanifica en la issue. No se absorbe en silencio el bloque de la otra persona.

## Congelamiento

La noche del 30 de octubre de 2026 se deja de integrar funcionalidad. El 31 de octubre de 2026 a las 15:00 (hora de Santiago) se sube el encargo. Desde ese momento y hasta la evaluación del 3 de noviembre de 2026 nadie pushea al repositorio. La issue #17 pide registrar el SHA entregado.
