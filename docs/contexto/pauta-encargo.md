# Pauta del Encargo (Parcial 2)

Evaluación Parcial 2, Encargo, asignatura DSY1105. Pondera **8 %** de la asignatura. Modalidad en parejas. Es un avance intermedio de la misma aplicación, no un proyecto distinto. El trabajo incompleto o fuera de plazo queda con la nota mínima.

Escala de la rúbrica: 100 %, 80 %, 60 %, 30 % y 0 %. La tabla de indicadores no usa la columna de 80 %. Un nivel “-” en el documento oficial significa que ese tramo no aplica.

## Fechas vigentes

| Hito | Cuándo |
|---|---|
| Corte Nivel 1 | Martes 27 de octubre de 2026 |
| Corte Nivel 2 | Jueves 29 de octubre de 2026, 23:59 |
| Nivel 3, README y prueba final en emulador | Viernes 30 de octubre de 2026 |
| Congelamiento del código | Viernes 30 de octubre de 2026, 23:59 |
| Trámites AVA (sin pushes) | Sábado 31 de octubre de 2026, antes de las 15:00 |
| Subida del encargo | 31 de octubre de 2026, 15:00, hora de Santiago |
| Evaluación posterior | 3 de noviembre de 2026 |

El 21 de octubre de 2026 figura en el informe del grupo y **no** rige. El equipo fija el congelamiento el viernes 30 a las 23:59; lo no terminado se saca, no queda a medias.

La pauta es explícita: si alguien del equipo modifica el repositorio después de la entrega y antes de la evaluación, aunque el cambio sea mínimo, la nota grupal y la individual pasan a **1,0** de forma automática. Por eso `main` se congela y se anota el commit exacto subido. Este pack no autoriza commits durante esa ventana.

## Indicadores

| Indicador | Peso | Desempeño completo (100 %) | Cómo lo evidencia el repo |
|---|---|---|---|
| **IE 2.1.1** Interfaz coherente, jerárquica, bien distribuida y con navegación funcional. | 15 % | Usabilidad visible, navegación fluida entre vistas. | Material 3, flujo Onboarding → Inicio → Explorar → Detalle → Favoritos → Perfil. Issues #3 (con accesibilidad base), #4 (cerrada), #8 y #9; letra al máximo y TalkBack se comprueban en la #17. |
| **IE 2.1.2** Formularios con validación visual por campo, mensajes e íconos. | 15 % | Todos los formularios del alcance, cada campo con error claro e ícono. Menos del 60 % de los formularios cae a 0 %. | Onboarding y perfil (nombre, correo, teléfono opcional, división, tamaño e intereses) y «Quiero que me contacten». Error con ícono y texto simple junto al campo, no solo color. Issues #6 y #24. |
| **IE 2.2.1** Validación centralizada, desacoplada de la UI, y la interfaz responde al estado. | 10 % | Reglas en la capa lógica; la UI reacciona al estado sin acoplarse. | Reglas en el ViewModel, un `StateFlow` por flujo, Composables sin decidir validez. Issues #7 y #24. |
| **IE 2.2.2** Animaciones que aportan fluidez o feedback. | 10 % | Transiciones o respuestas útiles durante la interacción. Animaciones mínimas que no aportan quedan en 30 %. | Feedback de favorito, validación o cambio de contenido, con APIs de Compose. Issue #14. |
| **IE 2.3.1** Modularidad (responsabilidades separadas) y persistencia local integrada. | 15 % | MVVM claro y datos locales que la UI usa de verdad. | Paquetes `ui`, `viewmodel`, `model`, `repository` y Room en `data/local`. Perfil y favoritos sobreviven al reinicio. Issues #1 (hecha), #5, #10 y #27. |
| **IE 2.3.2** GitHub y planificación, con participación de las dos personas. | 20 % | Commits distribuidos, issues resueltas y tareas visibles. Si solo se ve a una persona, el indicador baja. | Issues, ramas, PR con revisión cruzada, commits desde `MainSteps` y desde `MatiasU-24`, GitHub Projects. Issues #2 y #16 (cerradas), #17 y #23, más el flujo de cada PR. |
| **IE 2.4.1** Al menos dos recursos del dispositivo, seguros y coherentes con la UI. | 15 % | Dos recursos funcionando, con permisos y encaje visual. Menos de dos, o uno inseguro, no alcanza el 100 %. | Cámara y galería en el perfil (el material del ramo las considera los recursos nativos), ambas Nivel 1: Activity Result, FileProvider, cancelación y permiso denegado. Compartir/WhatsApp con Intent es extra (#25). Issues #12, #28 y #25. |

El indicador de más peso es la colaboración (20 %). La evidencia tiene que acumularse con el trabajo real. No sirve abrir issues o reescribir el historial al final.

## Qué debe mostrar el repositorio el día de la subida

- Repositorio público, con acceso del docente y de los dos integrantes. El usuario GitHub del docente sigue pendiente: el informe lo marca así y en el repo no aparece otro colaborador.
- Carpeta del proyecto (`app`, Gradle, etc.).
- `README.md` con descripción, nombres, funcionalidades implementadas y pasos para ejecutar. El README actual describe el propósito y el stack previsto; al cerrar la issue #17 tiene que describir lo que realmente quedó implementado.
- Commits técnicos, repartidos entre las dos cuentas. Sin mensajes vacíos (“avance”, “listo”).
- Planificación en el [GitHub Project de MainSteps](https://github.com/users/MainSteps/projects/2), como herramienta similar a Trello (la pauta admite el equivalente). El informe del grupo registra que el profesor indicó que Trello no es obligatorio. No se mantiene un segundo tablero. El Project es de usuario; conviene vincularlo al repositorio.
- Un solo integrante sube el código al enlace grupal del AVA. El informe asigna esa subida a Diego. Cada uno activa además el enlace individual con nombre, apellido, número de equipo y nombre de la aplicación. Esos enlaces no están en este repositorio.
- SHA del commit congelado, anotado en la entrega. Ningún push posterior de ninguno de los dos.

El CI de compilación en cada PR ya existe (#16); igual se compila en local antes de abrir el pull request.
