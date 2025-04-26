<h1 align="center">RayaCash App</h1>

<p align="center">
  <a href="https://spdx.org/licenses/MIT.html"><img alt="License" src="https://img.shields.io/badge/License-MIT-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-26%2B-brightgreen.svg?style=flat"/></a><br>
  <a href="https://www.youtube.com/@GonzaloDroid2050"><img alt="Profile" src="https://img.shields.io/youtube/channel/subscribers/UCPjql8JlN5kw6hU2U_tngaw?style=social"/></a> 
</p>

<p align="center">  
RayaCash App, proyecto kotlin multiplataforma android/ios, Compose, Ktor, Koin, Coroutines, Flow
Room, ViewModel, Material3, arquitectura MVVM.
</p>

<p align="center">
<img src="previews/features.png"/>
</p>

## RayaCash App
RayaCash es una aplicación de finanzas desarrollada en [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html) para Android/iOS. Permite registrar transacciones, visualizar balances convertidos a pesos argentinos (ARS), y realizar conversiones entre monedas como USD, BTC y ETH.


## Features

-  Visualización de transacciones recientes
-  Conversión de montos entre monedas
-  Cálculo del balance total en ARS
-  Manejo de estados con `StateFlow`
-  UI moderna con `Jetpack Compose` y `Material3`
-  Arquitectura limpia con `MVVM`
-  Reutilización de lógica compartida en Android/iOS

## Tech Stack

- **SDK minSdk:** 24.  
- **SDK targetSdk:** 35.  
- **Kotlin Multiplatform (KMP)** Reutilización de lógica de negocio en Android e iOS [Kotlin KMP](https://kotlinlang.org/lp/multiplatform/)  -
- **Ktor** Cliente HTTP asíncrono multiplataforma para realizar llamadas a APIs [Ktor](https://ktor.io/)
- **Koin** Inyección de dependencias liviana y flexible [Koin](https://insert-koin.io/)                          
- **Material3** Implementación del sistema de diseño Material Design actualizado [Material3](https://m3.material.io/) 
- Basado en [Kotlin](https://kotlinlang.org/), utilizando [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) para operaciones asíncronas.  

- **Jetpack Compose:** Kit de herramientas moderno de Android para desarrollo de UI declarativa.  
- **Lifecycle:** Observa los ciclos de vida de Android y gestiona los estados de UI ante cambios de ciclo de vida.  
- **ViewModel:** Administra datos relacionados con la UI y es consciente del ciclo de vida, asegurando la persistencia de datos tras cambios de configuración.  
- **Navigation:** Facilita la navegación entre pantallas, complementado con [Hilt Navigation Compose](https://developer.android.com/jetpack/compose/libraries#hilt) para inyección de dependencias.  
- **Room:** Permite construir una base de datos con una capa de abstracción sobre SQLite para un acceso eficiente a los datos.  
- **Arquitectura MVVM (View - ViewModel - Model):** Promueve la separación de responsabilidades y mejora el mantenimiento del código.  
- **Patrón Repository:** Actúa como mediador entre diferentes fuentes de datos y la lógica de negocio de la aplicación.  
- **[Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization):** Serialización sin reflejos para múltiples plataformas y formatos en Kotlin.  
- **[ksp](https://github.com/google/ksp):** API de procesamiento de símbolos en Kotlin para generación y análisis de código.  



## Architecture
**RayaCash App** sigue la aquitectura MVVM e implementa patrón Repository, alineado con [Guía oficl de arquitectura de Google](https://developer.android.com/topic/architecture).

La arquitectura de **RayaCash App** está estructurada en dos capas distintas: la capa de UI y la capa de datos. Cada capa cumple roles y responsabilidades específicas, que se describen a continuación.  

- `Model`: Repositorios, acceso a la base de datos (Room), y clientes de red (Ktor)
- `ViewModel`: Lógica de presentación, manejo de estados con `StateFlow`
- `View`: Composables que representan la UI, observan el estado y reaccionan a eventos

## 📁 Project Structure

```plaintext
RayaCash/
├── androidApp/               # Código específico de Android
│   ├── ui/                   # Composables y navegación con Jetpack Compose
│   ├── viewmodel/            # ViewModels específicos de Android
│   └── di/                   # Inyección de dependencias para Android
├── iosApp/                   # Código específico de iOS (SwiftUI/Combine si aplica)
├── shared/                   # Módulo multiplataforma (Kotlin común)
│   ├── data/
│   │   ├── repository/        # Repositorios que acceden a local y remote
│   │   ├── local/             # Implementación de Room Database
│   │   ├── remote/            # Implementación de Ktor Client para APIs
│   ├── domain/
│   │   ├── model/             # Entidades y modelos de negocio
│   │   ├── usecase/           # Casos de uso de la aplicación
│   ├── presentation/
│   │   ├── state/             # Clases de estado de la UI
│   │   ├── event/             # Clases de eventos de la UI
│   │   ├── viewmodel/         # ViewModels multiplataforma
│   ├── di/                    # Inyección de dependencias común (Koin)
│   ├── utils/                 # Utilidades y extensiones compartidas
├── build.gradle.kts           # Configuración de la raíz del proyecto
└── settings.gradle.kts        # Configuración de módulos
```



## 🛠️ Instalación y Configuración  

##### 1️⃣ Clone Repository
```bash
git clone https://github.com/gonzalo-droid/rayaCashApp
```
##### 2️⃣ Generar tu Clave de API KEY en Coingecko
- Visita https://docs.coingecko.com/v3.0.1/reference/introduction
- Regístrate o inicia sesión.
- Dirígete a la sección API de tu cuenta y genera una nueva clave de API
##### 3️⃣ Agregar la Clave de API en /commonMain/di/DataModule.kt
- En la raíz del proyecto, crea (o actualiza) un archivo llamado local.properties y agrega la siguiente línea:
```bash
const val COIN_GECKO_API_KEY = "TU_API_KEY"
const val API_HOST = "api.coingecko.com"
const val API_KEY = "x-cg-demo-api-key"
```
##### 4️⃣ Compilar y Ejecutar el Proyecto
- Usa Gradle para compilar y ejecutar el proyecto:
```bash
./gradlew run
```
Para Android, abre el proyecto en Android Studio y ejecuta la aplicación desde allí. 
** Recuerda que para iOS necesitar una mac y así emular en un iphone


## 🚀 ¡Contribuciones bienvenidas!  

💡 **Si quieres proponer mejoras o corregir errores:**  
1. Haz un *fork* del repositorio.  
2. Crea una rama con tu mejora.
  ```bash
   git checkout -b feature/your-feature-name
  ```
3. Realiza los cambios y haz un *commit*.
 ```bash
   git commit -am 'Add some feature'
   ```  
4. Sube los cambios a tu repositorio.
 ```bash
   git push origin feature/your-feature-name
   ```  
5. Abre un *Pull Request* para revisión.  


## Sigamos en contacto

✨ **Espero que este proyecto te sea útil para seguir aprendiendo.**  
💡 ¡Puedes colaborar en mejoras del proyecto dejando un *Pull Request*!  
⭐ Además, agradecería mucho que le dieras una estrella al proyecto 🤩 


Aún estoy definiendo el formato 🫠, pero lo importante es empezar. 
¡Suscríbete y vamos a codear!
- [YouTube](https://www.youtube.com/@gonzalolock)
- [TikTok](https://www.tiktok.com/@gonzalock.dev)
- [LinkedIn](https://www.linkedin.com/in/gonzalo-lozg/)


## 🚧 Próximas mejoras

- [ ] Soporte para autenticación, Google Auth
- [ ] Filtros en las transacciones
- [ ] Gráficas Traker

## License 

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.
