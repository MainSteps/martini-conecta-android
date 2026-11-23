# Recorrido de la issue #51

Verificado el 22/11/2026 (Santiago), con APK del commit `5667380`, emulador Android API 35 y `martini-api` local en `10.0.2.2:8080` (backend `2980a0c`). Se usaron un perfil ficticio y una solicitud creada como dato de prueba en la API.

| Caso | Resultado observado |
| --- | --- |
| Abrir Mis solicitudes | Carga la solicitud correspondiente al teléfono del perfil |
| Guardar comuna de un carácter | Muestra “Ingresa tu comuna”; GET conserva el dato anterior |
| Editar comuna, producto y estado | La tarjeta y GET muestran Providencia, BRAVO_CREM y CONTACTADO |
| Cancelar eliminación | La solicitud continúa visible y GET devuelve 200 |
| Guardar con API apagada | Muestra error, mantiene diálogo y lista |
| Eliminar con API apagada | Muestra error, mantiene diálogo y lista; el registro sigue existiendo al reiniciar API |
| Reintentar eliminación con API encendida | La lista pasa a vacía y GET `/api/solicitudes/4` devuelve 404 |

Evidencias:

- [Edición guardada](51-edicion.png)
- [Error de PUT sin API](51-error-put.png)
- [Error de DELETE sin API](51-error-delete.png)
- [Estado vacío tras eliminar](51-eliminada.png)

El primer intento en el AVD API 37 tuvo timeout hacia `10.0.2.2`, como estaba documentado en #55. Se preparó API 35 y se repitió el recorrido con el mismo APK, sin modificar el código ni los permisos del AVD API 37. El resultado anterior corresponde a API 35.
