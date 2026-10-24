# Martini Conecta

Prototipo móvil académico desarrollado para el caso Master Martini en **DSY1105 — Desarrollo de Aplicaciones Móviles**, sección 003V, grupo 15.

## Propósito

App de contenidos técnicos para clientes y emprendedores de panadería, pastelería, heladería y gastronomía de Master Martini Chile (filial de Unigrà). Lleva del contenido al producto Martini y del producto a una conversación con ventas por WhatsApp. Diseñada para usuarios mayores de 50, con bajo manejo digital o dificultades visuales: simple, accesible y disponible sin conexión.

## Alcance del MVP

- Perfil local con división, tamaño de negocio, intereses y teléfono opcional.
- Catálogo de recetas por división con productos Martini, rinde e ingredientes.
- Exploración con un filtro y búsqueda simple; detalle de receta.
- Favoritos que se ven sin internet.
- Formulario «Quiero que me contacten».
- Foto de perfil desde galería o cámara.
- Inicio por división, consultar a ventas por WhatsApp y escalar receta (diferenciadores).
- Accesibilidad: respeta la letra del sistema, alto contraste, botones grandes, TalkBack.

## Tecnologías previstas

Kotlin, Jetpack Compose, Material 3, Navigation Compose, ViewModel, StateFlow, Room, Activity Result APIs.

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
