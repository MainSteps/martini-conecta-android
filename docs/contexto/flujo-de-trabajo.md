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

Asignación vigente desde la reestructuración del 24 de octubre de 2026 (alcance Martini, niveles 1/2/3). Las issues cerradas no se listan.

Cada integrante toca las cuatro capas: interfaz en Compose, lógica y estado con ViewModel, persistencia y un recurso nativo.

### Diego Alonso Pazos Atarama (`MainSteps`)

| Orden | Issue | Tema | Capa | IE |
|---|---|---|---|---|
| 1 | #7 | Validación y estado del perfil | ViewModel | 2.2.1 |
| 2 | #6 | Onboarding y perfil | UI | 2.1.2 |
| 3 | #10 | Room: base de datos y perfil | Datos | 2.3.1 |
| 4 | #12 | Galería | Nativo | 2.4.1 |
| 5 | #14 | Animaciones de feedback | UI | 2.2.2 |
| N2 | #8 | Inicio por división | UI y estado | 2.1.1 |
| N2 | #25 | Compartir y consultar a ventas | Nativo | 2.4.1 |

### Matías Urrejola (`MatiasU-24`)

| Orden | Issue | Tema | Capa | IE |
|---|---|---|---|---|
| 1 | #3 | Tema M3 Martini + accesibilidad | UI | 2.1.1 |
| 2 | #5 | Catálogo Martini | Datos | 2.3.1 |
| 3 | #9 | Explorar y detalle (N1; N2 en detalle) | UI y ViewModel | 2.1.1 |
| 4 | #27 | Favoritos en Room | Datos y ViewModel | 2.3.1 |
| 5 | #24 | «Quiero que me contacten» | UI, ViewModel y datos | 2.1.2 y 2.2.1 |
| 6 | #28 | Cámara (botón secundario) | Nativo | 2.4.1 |

### Los dos

| Issue | Tema |
|---|---|
| #17 | README, verificación final y congelamiento |
| #23 | Trámites AVA grupal e individual |

`CODEOWNERS` lista a los dos en todo el árbol.

## Orden de dependencia

Ninguno empieza esperando al otro: Diego arranca por #7 y #6, Matías por #3, #5 y #9. Diego crea la `AppDatabase` en #10; Matías agrega favoritos en #27 (si llega antes, crea la base y Diego suma perfil). La cámara (#28) reutiliza el evento de foto de la galería (#12). Compartir y consultar a ventas (#25) van sobre el detalle de #9. Primero se cierra todo el Nivel 1 de cada uno; el Nivel 2 empieza después. La animación de favorito (#14) llega después de #27. Cierran #17 y #23.

Si una tarea se atrasa, se dice en la issue. No se absorbe en silencio el bloque de la otra persona.

## Niveles y congelamiento

- **Nivel 1 (pauta):** cerrado el martes 27 de octubre de 2026.
- **Nivel 2 (diferenciador):** hasta el jueves 29 de octubre de 2026 a las 23:59.
- **Viernes 30:** Nivel 3 (letra grande, pulido), README y verificación (preparados en paralelo con IA) y prueba final en emulador con letra al máximo y TalkBack.
- **Viernes 30 de octubre de 2026, 23:59: congelamiento del código.** Lo que no esté terminado se saca, no queda a medias. La #17 registra el SHA.
- **Sábado 31:** solo trámites del AVA (#23) antes de las 15:00 (hora de Santiago). Desde el congelamiento y hasta la evaluación del 3 de noviembre de 2026 nadie pushea al repositorio.
