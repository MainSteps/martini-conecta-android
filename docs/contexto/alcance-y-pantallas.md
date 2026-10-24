# Alcance y pantallas

Martini Conecta es el MVP Android del grupo 15 para el caso Master Martini: lleva al usuario **del contenido al producto y del producto a una conversación con ventas**. Sin cuenta ni servidor.

## Usuario y empresa

- **Usuario:** clientes y emprendedores de panadería, pastelería, chocolatería, heladería y gastronomía. Parte importante tiene más de 50 años, bajo manejo digital o dificultades visuales.
- **Empresa:** Master Martini Chile, filial de la italiana Unigrà. Vende insumos a negocios en 4 divisiones: Panadería & Pastelería, Gelatería, Food Service y Retail. Productos reales: Manteca Unigral, Margarina Gran Master Hoja, Bravo Crem, Monnalisa, Chocolate Caravella, Chocolate Ariba, línea vegana Orasì. Su contacto principal es WhatsApp "Habla con ventas" (`+56 9 4457 0770`). No publica recetas, videos ni academia en Chile: ese es el hueco que cubre la app.
- **Éxito para la empresa:** más contacto con clientes, datos de perfil segmentados, difusión de marca y una experiencia simple y accesible.

## Reglas de diseño (transversales)

1. **Una acción principal por pantalla.**
2. **Cualquier receta en ≤ 3 toques** desde Inicio.
3. **Accesibilidad base en todo** (issue #3): texto en `sp` que respeta la letra del sistema, cuerpo ≥ 16 sp, contraste ≥ 4.5:1, áreas táctiles ≥ 48 dp, íconos siempre con texto, `contentDescription`, errores con ícono y texto simple. Nada se corta con la letra al máximo.
4. Opciones para elegir (botones, chips) antes que texto libre.

## Niveles y fechas de corte

| Nivel | Qué | Corte |
|---|---|---|
| 1: pauta | Todo lo que asegura el 100 % de la rúbrica, ya construido para Martini y su usuario. | Martes 27/10 |
| 2: diferenciador | Inicio por división, productos usados, "Consultar a ventas", escalar receta, compartir. | Jueves 29/10 23:59 |
| 3: extra | Preferencia "Letra grande" y pulido visual. | Viernes 30/10 |

- Viernes 30/10: Nivel 3, README y verificación (preparados en paralelo con IA) y prueba final en emulador (letra al máximo + TalkBack).
- **Viernes 30/10 23:59: congelamiento del código.** Lo que no esté terminado se saca, no queda a medias.
- Sábado 31/10: solo trámites del AVA, antes de las 15:00. Sin pushes.

## Funcionalidades

| Función | Nivel | Issue | Comportamiento |
|---|---|---|---|
| Tema y accesibilidad | 1 | #3 | Material 3 con colores Martini (modo demo) y reglas de accesibilidad. |
| Catálogo | 1 | #5 | 9–12 recetas realistas por división, con productos Martini, rinde e ingredientes. |
| Onboarding y perfil | 1 | #6, #7, #10 | Nombre, correo, teléfono opcional, división, tamaño (3 botones), intereses (chips). |
| Explorar y detalle | 1 | #9 | Un filtro en chips, búsqueda simple, detalle con ingredientes y pasos. |
| Favoritos | 1 | #27 | Guardan el contenido completo; se ven sin red. |
| "Quiero que me contacten" | 1 | #24 | Segundo formulario validado: nombre, WhatsApp, división, comuna, producto de interés. |
| Foto de perfil | 1 | #12, #28 | Galería (principal) y cámara (botón secundario). |
| Animaciones | 1 | #14 | Feedback corto: favorito, validación, transición. |
| Inicio por división | 2 | #8 | "Para tu panadería", etc. |
| Productos usados, consultar a ventas, escalar receta | 2 | #9, #25 | En el detalle. |
| Compartir receta | 2 | #25 | `ACTION_SEND`. |
| Letra grande | 3 | #10 | Preferencia guardada en el perfil. |

## Reglas de negocio

- Un perfil local, sin login.
- Nombre y correo obligatorios. Teléfono **opcional**, formato chileno `+56 9 XXXX XXXX`, datos ficticios en pruebas.
- División, tamaño ("Solo yo", "2 a 10 personas", "Más de 10") y al menos un interés. El tamaño sirve a la empresa, no personaliza la UI.
- Favoritos sin duplicados; la acción alterna.
- El número de ventas vive en una sola constante.
- RUT y dirección quedan fuera.

## Marca

Se usan nombres de productos, colores (azul `#1B80C5`, a confirmar con el logo) y el WhatsApp público de Martini en **modo demo**, con el aviso visible "Prototipo académico, no oficial".

## Recursos nativos (IE 2.4.1)

El material del ramo considera cámara y galería como los recursos nativos: los dos son Nivel 1 (#12, #28), con Activity Result, FileProvider, permiso explicado y cancelación segura. Compartir/WhatsApp con Intent (#25) es extra.

## Fuera del MVP

Login, backend, sincronización, pagos, push, analítica, chat, puntos de fidelidad, video real, IA dentro de la app, DataStore.
