# Tactical Hoops 2

Una aplicación Android para crear jugadas de baloncesto en una pizarra 2D y compartirlas en un chat con otros integrantes del equipo.

## Características

### Pizarra 2D de Baloncesto
- Vista de cancha de baloncesto con líneas regulares
- Agregar jugadores del Equipo 1 (azul) y Equipo 2 (rojo)
- Agregar balones de baloncesto
- Dibujar flechas para indicar movimientos
- Mover jugadores arrastrando
- Limpiar la pizarra

### Sistema de Chat
- Enviar mensajes de texto
- Compartir jugadas creadas en la pizarra
- Ver jugadas compartidas en el chat
- Cargar jugadas desde el chat de vuelta a la pizarra

## Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/tacticalhoops/
│   │   ├── MainActivity.kt                 # Actividad principal con tabs
│   │   ├── BoardFragment.kt                # Fragmento de la pizarra
│   │   ├── ChatFragment.kt                 # Fragmento del chat
│   │   ├── models/
│   │   │   ├── Play.kt                     # Modelos de datos para jugadas
│   │   │   └── ChatMessage.kt              # Modelo de mensajes de chat
│   │   ├── views/
│   │   │   └── BasketballCourtView.kt      # Vista personalizada de la cancha
│   │   └── adapters/
│   │       └── ChatAdapter.kt              # Adaptador para RecyclerView del chat
│   └── res/
│       ├── layout/                         # Layouts XML
│       └── values/                         # Recursos de strings, colores, temas
```

## Tecnologías Utilizadas

- **Kotlin** - Lenguaje de programación principal
- **Android SDK** - Framework de Android
- **Material Design** - Componentes de UI
- **Canvas API** - Dibujo personalizado de la cancha
- **RecyclerView** - Lista de mensajes del chat

## Requisitos

- Android Studio Arctic Fox o superior
- Gradle 8.0
- Android SDK 24+ (Android 7.0 Nougat o superior)
- Kotlin 1.8.0

## Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/daguerma/Tactical-Hoops-2.git
cd Tactical-Hoops-2
```

1. Abrir el proyecto en Android Studio

2. Sincronizar las dependencias de Gradle

3. Ejecutar la aplicación en un emulador o dispositivo físico

## Uso

### Crear una Jugada

1. Abre la pestaña "Board"
2. Selecciona "Team 1" o "Team 2" para agregar jugadores
3. Toca en la cancha para colocar jugadores
4. Usa "Add Ball" para agregar un balón
5. Usa "Draw Arrow" y arrastra para crear flechas de movimiento
6. Presiona "Share Play" para compartir la jugada en el chat

### Ver y Compartir en el Chat

1. Cambia a la pestaña "Chat"
2. Escribe mensajes o visualiza las jugadas compartidas
3. Toca una jugada compartida para cargarla en la pizarra
4. Los mensajes muestran la hora y el remitente

## Características Futuras

- Persistencia de datos (guardar jugadas localmente)
- Exportar jugadas como imágenes
- Integración con Firebase para chat en tiempo real
- Múltiples usuarios en línea
- Diferentes tipos de canchas (media cancha, cancha completa)
- Más herramientas de dibujo (líneas, zonas, anotaciones de texto)
