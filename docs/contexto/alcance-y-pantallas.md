# Alcance y pantallas

Martini Conecta es el MVP Android del grupo 15 para el caso Master Martini. Centraliza contenido técnico ficticio, ordena el inicio según los intereses del perfil y guarda favoritos en el dispositivo, sin cuenta ni servidor.

## Funcionalidades

| Función | Comportamiento | Qué demuestra |
|---|---|---|
| Configuración inicial | Nombre, correo, tipo y tamaño de negocio e intereses. Valida cada campo. | Formulario, StateFlow, Room |
| Inicio | Saludo, categorías y destacados según los intereses guardados. | Material 3 y estado |
| Exploración | Lista ficticia, búsqueda y filtros por categoría o formato. | Estado y listas |
| Detalle | Título, descripción, categoría, formato, duración o dificultad, favorito y compartir. | Navegación, animación y recurso nativo (Intent) |
| Favoritos | Alta y baja persistidas. Estado vacío que explica cómo guardar. | Room y repositorio |
| Perfil | Editar datos e intereses. Foto desde cámara o galería. | Validación, permisos, recursos nativos |
| Sugerir contenido | Tema, categoría, formato y comentario. Valida cada campo con error e ícono. Se guarda en local. | Segundo formulario, validación desacoplada |
| Animaciones | Feedback al marcar favorito, al validar, en mensajes de éxito o al cambiar el contenido. | Retroalimentación visual |

En el PR #20 esas pantallas son esqueletos con texto de relleno. La barra inferior cubre Inicio, Explorar, Favoritos y Perfil. Onboarding y Detalle van sin barra. El recorrido previsto es: primer ingreso al onboarding, validación, guardado local, inicio personalizado, exploración, detalle, favorito, favoritos que sobreviven al reinicio, y perfil con imagen.

## Reglas de negocio

- Un perfil local. Sin login, sin varias cuentas y sin recuperar contraseña.
- Nombre obligatorio, con un largo mínimo razonable. Correo obligatorio y con formato válido.
- Hay que elegir tipo de negocio, tamaño aproximado y al menos un interés.
- Un contenido no queda dos veces en favoritos. La acción alterna guardar y quitar.
- La foto sale de la cámara o de la galería y se muestra en la interfaz.
- Teléfono: el informe lo deja como campo opcional solo si el formulario sigue claro. Las issues #6 y #7 no lo incluyen. No lo agregues por iniciativa propia.
- RUT de empresa y dirección quedan fuera.

## Contenido de demostración

Entre 9 y 12 piezas ficticias, con id, título, descripción breve, categoría, formato, dificultad, duración estimada, imagen local y etiquetas. Sin texto de relleno del tipo “lorem”. Ninguna se presenta como receta o curso oficial de Master Martini. El modelo queda listo para, en otro proyecto, cambiar la fuente local por una API. Esa API no forma parte de este MVP.

## Recursos nativos

Cámara y galería, las dos dentro del perfil: contratos de Activity Result, URI segura con FileProvider para la captura, permiso denegado explicado en pantalla, y cancelar sin cerrar la app. No se pide GPS ni otros sensores.

## Fuera del MVP

Inicio de sesión real, backend, sincronización, panel de administración, pagos, notificaciones push, analítica, transmisión real de video, recomendaciones automáticas e inteligencia artificial dentro de la app. Tampoco logos, videos o manuales de marca mientras no haya autorización explícita.

## Experiencia

Jerarquía clara, acciones visibles, pocos pasos y vuelta atrás predecible. Texto legible con el escalado del sistema, contraste suficiente, áreas táctiles cómodas y errores escritos junto al campo. Tiene que poder completar el perfil y encontrar un contenido alguien con poca experiencia digital, sin un instructivo al lado.
