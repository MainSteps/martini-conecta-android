# Guía de contribución

## Flujo de una tarea

1. Seleccionar una issue asignada y comprobar sus dependencias.
2. Crear una rama desde `main`.
3. Implementar cambios pequeños y comprensibles.
4. Compilar y ejecutar las pruebas aplicables.
5. Abrir un pull request vinculado con la issue.
6. Solicitar revisión al integrante que no sea autor.
7. Corregir observaciones y fusionar mediante **Squash and merge**.
8. Eliminar la rama fusionada y cerrar la issue.

## Nomenclatura de ramas

- `feat/<numero>-<descripcion>`
- `fix/<numero>-<descripcion>`
- `docs/<numero>-<descripcion>`
- `test/<numero>-<descripcion>`
- `chore/<numero>-<descripcion>`

Ejemplo: `feat/12-formulario-perfil`.

## Commits

Usar mensajes descriptivos con un tipo claro:

- `feat: agrega validación de correo`
- `fix: conserva favoritos al reiniciar`
- `docs: documenta ejecución local`
- `test: cubre validaciones del perfil`
- `chore: configura dependencias de Room`

No mezclar cambios sin relación ni usar mensajes como “avance”, “cambios” o “listo”.

## Pull requests

Cada pull request debe:

- vincular la issue con `Closes #número`;
- explicar qué cambia y por qué;
- indicar cómo se verificó;
- incluir capturas o video si modifica la interfaz;
- evitar archivos generados, secretos y configuración local del IDE;
- recibir revisión del integrante que no sea autor.

## Definición de terminado

Una tarea está terminada cuando:

- cumple sus criterios de aceptación;
- funciona en casos normales y errores razonables;
- respeta MVVM y no concentra reglas en Composables;
- no rompe funcionalidades existentes;
- compila y supera las comprobaciones aplicables;
- tiene evidencia en commits y pull request;
- fue revisada y puede ser explicada por su responsable.
