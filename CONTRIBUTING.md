# Guía de contribución

## Flujo de una tarea

1. Seleccionar una issue asignada y comprobar sus dependencias.
2. Crear una rama desde `main`.
3. Implementar cambios pequeños y comprensibles.
4. Compilar y recorrer el flujo en el emulador.
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

- Vincula la issue con `Closes #número` y explica qué cambia.
- Compila en local y recorre el flujo en el emulador; la captura es opcional.
- Revisa y aprueba el integrante que no es autor. El check de CI debe estar en verde.
- Se fusiona con **Squash and merge**.

Una tarea está terminada cuando cumple los criterios de su issue y su responsable puede explicarla.
