# Flujo de trabajo

Grupo 15. Propietario del repositorio: `MainSteps`. Colaborador: `MatiasU-24`. Rama por defecto: `main`. Visibilidad: pública.

La planificación vive en GitHub Issues y en el [GitHub Project de MainSteps](https://github.com/users/MainSteps/projects/2), con los estados Backlog, Por hacer, En curso, En revisión, Bloqueado y Terminado. La pauta pide "Trello o similar": este Project cumple ese papel. No abras otro tablero.

## Reglas que no se negocian en un PR

1. Cada persona trabaja y hace commit desde su propia cuenta. Diego no entrega código como Matías, ni al revés. La rúbrica mira la participación de los dos.
2. Una issue por pull request. La rama sale de `main` actualizada.
3. Revisión cruzada: aprueba quien no escribió el cambio y el CI está en verde. Fusión con **Squash and merge**; se borra la rama.
4. Sin commits directos a `main`.
5. Mensajes de commit con tipo y alcance técnico, en español (`feat:`, `fix:`, `docs:`, `test:`, `chore:`).
6. No se suben carpetas de build, secretos, `.idea/` ni datos personales.


## Ramas

| Prefijo | Cuándo |
|---|---|
| `feat/<numero>-<descripcion>` | Funcionalidad de esa issue |
| `fix/<numero>-<descripcion>` | Corrección |
| `docs/<numero>-<descripcion>` | Documentación atada a una issue |
| `test/<numero>-<descripcion>` | Pruebas |
| `chore/<numero>-<descripcion>` | Configuración o mantenimiento |

El pull request enlaza `Closes #<n>`, dice qué cambia y confirma compilación local y recorrido en emulador. Plantilla: `.github/PULL_REQUEST_TEMPLATE.md`.

## Dueños

Asignación vigente desde la reorganización del 22 de octubre de 2026. Las issues cerradas no se listan.

### Diego Alonso Pazos Atarama (`MainSteps`)

| Issue | Tema | IE |
|---|---|---|
| #6 | Onboarding y formulario de perfil | 2.1.2 |
| #7 | Validación y estado del perfil | 2.2.1 |
| #8 | Inicio personalizado | 2.1.1 |
| #14 | Animaciones | 2.2.2 |
| #16, PR #22 | CI en pull requests | 2.3.2 |
| #24 | Segundo formulario: sugerir contenido | 2.1.2 |

### Matías Urrejola (`MatiasU-24`)

| Issue | Tema | IE |
|---|---|---|
| #3 | Tema Material 3 y componentes | 2.1.1 |
| #5 | Modelo y catálogo local | 2.3.1 |
| #9 | Exploración, búsqueda y detalle | 2.1.1 |
| #10 | Room de perfil y favoritos, con pantalla Favoritos | 2.3.1 |
| #12 | Cámara y galería | 2.4.1 |
| #25 | Compartir con Intent | 2.4.1 |

### Los dos

| Issue | Tema |
|---|---|
| #17 | README, verificación final y congelamiento |
| #23 | Trámites AVA grupal e individual |

`CODEOWNERS` lista a los dos en todo el árbol.

## Orden de dependencia

Tema (#3) y catálogo (#5) desbloquean Inicio (#8) y Explorar (#9). Validación (#7) va con el formulario (#6). Room (#10) necesita perfil y catálogo; de él dependen cámara y galería (#12), sugerencias (#24) y animación de favorito (#14). Compartir (#25) necesita el detalle (#9). Cierran #17 y #23.

Si una tarea se atrasa, se dice en la issue. No se absorbe en silencio el bloque de la otra persona.

## Congelamiento

La noche del 30 de octubre de 2026 se deja de integrar funcionalidad. El 31 de octubre de 2026 a las 15:00 (hora de Santiago) se sube el encargo. Desde ese momento y hasta la evaluación del 3 de noviembre de 2026 nadie pushea al repositorio. La #17 registra el SHA entregado y la #23 cubre el AVA grupal y el individual de cada uno.
