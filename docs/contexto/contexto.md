# Contexto de Martini Conecta

Documento único de contexto para personas y agentes de IA. Si contradice al código de la rama, manda el código y se corrige este archivo en el mismo PR. Las tareas concretas viven en las [issues](https://github.com/MainSteps/martini-conecta-android/issues) y en el [GitHub Project](https://github.com/users/MainSteps/projects/2).

## 1. Caso y empresa

- **Ramo:** DSY1105 Desarrollo de Aplicaciones Móviles, Duoc UC, sección 003V, grupo 15. Encargo Parcial 2 (8 %), en parejas.
- **Equipo:** Diego Pazos (`MainSteps`) y Matías Urrejola (`MatiasU-24`).
- **Empresa:** Master Martini Chile SPA, filial de la italiana Unigrà. Vende insumos a negocios con el discurso "más rendimiento, ahorro y calidad italiana".
- **Divisiones:** Panadería & Pastelería, Gelatería, Food Service (hoteles, casinos, cafeterías) y Retail.
- **Productos reales:** Manteca Unigral, Margarina Gran Master Hoja, Bravo Crem, Monnalisa, Chocolate Caravella, Chocolate Ariba, línea vegana Orasì.
- **Canal de ventas:** WhatsApp "Habla con ventas", `+56 9 4457 0770`. No tiene tienda online, recetas, videos ni academia en Chile.
- **Problema del caso:** la empresa quiere acercar clientes y prospectos con contenido técnico (recetas, videos, cursos), juntar datos de perfil segmentados, difundir marca y productos y abrir contacto. Hoy manda videos sueltos por WhatsApp.
- **Entregable:** MVP académico, no app productiva. No se presenta como contratado ni aprobado por Martini.

**Idea de la app:** del contenido al producto, y del producto a una conversación con ventas.

## 2. Usuario objetivo

Dueños y trabajadores de panaderías, pastelerías, heladerías, chocolaterías, casinos y restaurantes. Parte importante tiene **más de 50 años, bajo manejo digital o dificultades visuales**. Usan WhatsApp a diario y consultan recetas en la cocina, a veces sin señal.

## 3. Reglas de diseño y accesibilidad (transversales)

1. **Una acción principal por pantalla.**
2. **Cualquier receta en ≤ 3 toques** desde Inicio.
3. Elegir (botones, chips) antes que escribir.
4. Texto en `sp`, cuerpo ≥ 16 sp, sin alturas fijas en contenedores de texto.
5. Contraste ≥ 4.5:1; el color nunca es el único indicador.
6. Áreas táctiles ≥ 48 dp, separadas.
7. Íconos siempre con texto (barra inferior incluida).
8. `contentDescription` en imágenes e íconos con acción.
9. Errores de formulario con ícono + texto simple junto al campo, al salir del campo o al guardar.
10. Con la letra del sistema al máximo nada se corta; TalkBack lee todo.
11. Marca Martini (colores, productos, WhatsApp) en **modo demo** con aviso visible "Prototipo académico, no oficial".

## 4. Alcance por niveles

| Nivel | Significado | Corte |
|---|---|---|
| **1: pauta** | Asegura el 100 % de la rúbrica, ya hecho para Martini y su usuario. | Martes 27/10 |
| **2: diferenciador** | Lo que muestra valor a la empresa. | Jueves 29/10 23:59 |
| **3: extra** | Solo si sobra tiempo. | Viernes 30/10 |

| Función | Nivel | Issue |
|---|---|---|
| Tema Material 3 Martini + accesibilidad base | 1 | #3 |
| Catálogo de recetas por división | 1 | #5 |
| Onboarding y perfil | 1 | #6 |
| Validación y estado del perfil | 1 | #7 |
| Room: base y perfil | 1 | #10 |
| Explorar y detalle | 1 | #9 |
| Favoritos sin red | 1 | #27 |
| Formulario «Quiero que me contacten» | 1 | #24 |
| Galería (principal) y cámara (secundaria) | 1 | #12, #28 |
| Animaciones de feedback | 1 | #14 |
| Inicio con colecciones por división | 2 | #8 |
| Productos usados, consultar a ventas, escalar receta | 2 | #9 |
| Compartir receta y WhatsApp a ventas | 2 | #25 |
| Preferencia "Letra grande" | 3 | #10 |
| README, verificación y congelamiento | 1 | #17 |
| Trámites AVA | 1 | #23 |

**Reglas de negocio**
- Un perfil local, sin login.
- Nombre y correo obligatorios. Teléfono opcional, formato `+56 9 XXXX XXXX`, siempre ficticio en pruebas.
- División, tamaño ("Solo yo", "2 a 10 personas", "Más de 10") y al menos un interés. El tamaño es dato para la empresa; no personaliza la UI.
- Favoritos sin duplicados; la acción alterna.
- El número de ventas vive en una sola constante.

**Fuera del MVP:** login, backend, red, sincronización, pagos, push, analítica, chat, fidelización, video real, IA dentro de la app, DataStore, RUT, dirección, adaptabilidad por tamaño de ventana.

## 5. Arquitectura

Kotlin, Jetpack Compose, Material 3, Navigation Compose, ViewModel + `StateFlow`, Room, Activity Result APIs, FileProvider. MVVM. Paquete raíz `com.mainsteps.martiniconecta`, minSdk 24.

| Capa | Responsabilidad | Paquete |
|---|---|---|
| UI | Pinta estado y emite eventos. | `ui/screens`, `ui/components`, `ui/theme` |
| ViewModel | Un `StateFlow` por flujo, validación, acciones. | `viewmodel/` |
| Modelo | Perfil, Contenido, SolicitudContacto, estados. | `model/` |
| Repositorio | Única puerta de la UI a los datos. | `repository/` |
| Datos | Room: entidades, DAO, `AppDatabase`. | `data/local/` |
| Navegación | `Routes` (clase sellada) y un solo `NavHost`. | `navigation/` |

**Ya en `main`:** navegación (`Routes`, `AppNavigation`, `AppBottomBar`), esqueletos de pantallas, CI de compilación en PR (`.github/workflows/android-ci.yml`).
**Fuera del stack:** Hilt, Koin, Retrofit, Firebase, DataStore. Versiones nuevas solo en `gradle/libs.versions.toml` y dentro de la issue que las necesita.

## 6. Pauta (IE)

| IE | Peso | Qué exige al 100 % | Issues |
|---|---|---|---|
| 2.1.1 Interfaz y navegación | 15 % | UI coherente, jerárquica, navegación fluida. | #3, #8, #9 |
| 2.1.2 Formularios | 15 % | Cada campo con validación visual, mensaje e ícono. | #6, #24 |
| 2.2.1 Validación desacoplada | 10 % | Reglas fuera de la UI; la UI reacciona al estado. | #7, #24 |
| 2.2.2 Animaciones | 10 % | Animaciones que aportan feedback o fluidez. | #14 |
| 2.3.1 Modularidad y persistencia | 15 % | MVVM claro y datos locales usados por la UI. | #5, #10, #27 |
| 2.3.2 GitHub y planificación | 20 % | Commits de los dos, issues, PR, tablero (GitHub Projects en lugar de Trello). | #17, #23, todo PR |
| 2.4.1 Recursos nativos | 15 % | Dos o más recursos seguros e integrados. El material del ramo usa galería + cámara. | #12, #28 (+ #25 extra) |

Trabajo incompleto o fuera de plazo: nota mínima. Modificar el repo después de la entrega y antes de la evaluación: **1,0 automático**.

## 7. Calendario y cortes

| Cuándo | Qué |
|---|---|
| Martes 27/10 | Nivel 1 cerrado. |
| Jueves 29/10 23:59 | Nivel 2 cerrado. |
| Viernes 30/10 | Nivel 3, README y verificación (preparados en paralelo con IA), prueba final en emulador con letra al máximo y TalkBack. |
| **Viernes 30/10 23:59** | **Congelamiento del código.** Lo no terminado se saca, no queda a medias. Se anota el SHA en #17. |
| Sábado 31/10, antes de 15:00 | Solo trámites del AVA (#23). Sin pushes. |
| Martes 03/11 | Evaluación. Nadie pushea hasta después. |

Horas de Santiago.

## 8. Flujo de trabajo

**Tablero:** [GitHub Project 2](https://github.com/users/MainSteps/projects/2), sustituto de Trello. Columnas **Por hacer → En curso → Hecho**. Cada issue tiene responsable y label de nivel.

1. Toma una issue tuya del nivel más bajo pendiente y muévela a **En curso**.
2. Rama desde `main` actualizada: `feat/<n>-<descripcion>` (o `fix/`, `docs/`, `chore/`).
3. Commits en español con tipo (`feat:`, `fix:`, `docs:`, `chore:`), desde tu propia cuenta. Sin comentarios en el código ni trailer `Co-authored-by`.
4. `./gradlew assembleDebug` local y recorrido en emulador.
5. PR con `Closes #<n>`, revisor = la otra persona. CI en verde.
6. El revisor aprueba y fusiona con **Squash and merge**; la issue pasa sola a **Hecho**.

Si una issue se atrasa, se avisa en la issue. Nadie hace commits como la otra persona. Sin commits directos a `main`.
