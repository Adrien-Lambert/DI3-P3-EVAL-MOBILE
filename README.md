# Rick and Morty Locations

Application Kotlin Multiplatform (Android + Desktop) permettant d'explorer les **locations** de l'univers Rick and Morty via l'API publique [rickandmortyapi.com](https://rickandmortyapi.com).

## Fonctionnalités

- **Liste des locations** avec nom, type et dimension
- **Détail d'une location** avec la liste de ses résidents (avatar + nom)
- **Son de portail** joué à l'ouverture d'un détail
- **Navigation mobile** classique (écran par écran)
- **Layout master-detail** sur Desktop (liste + détail côte à côte)
- **Persistance locale** : les données sont cachées en base Room

## Architecture

Le projet suit une architecture **Clean Architecture** en 3 couches :

```
┌─────────────────────────────────────────────┐
│              Presentation (UI)              │
│  Screens, ViewModels, Theme, Composables    │
├─────────────────────────────────────────────┤
│                  Domain                     │
│       Models métier, Repository interface   │
├─────────────────────────────────────────────┤
│                   Data                      │
│  Remote (Ktor), Local (Room), Repository    │
└─────────────────────────────────────────────┘
```

### Patterns utilisés

- **UDF/MVI** : chaque écran a un `State`, des `Action` et un `ViewModel` qui gère le flux unidirectionnel
- **Repository Pattern** : abstraction via une interface dans le Domain, implémentation local-first dans Data
- **expect/actual** : pour les composants natifs (HttpClient engine, Room builder, DataStore, SoundManager)
- **Single Source of Truth** : l'UI lit toujours depuis Room, l'API sert de mécanisme de synchronisation

### Stratégie de fetch (local-first)

1. L'UI observe un `Flow` depuis la base Room
2. Si la base est vide, toutes les pages sont fetchées depuis l'API
3. Les données sont sauvegardées en Room
4. Le Flow émet automatiquement les mises à jour

## Structure des packages

```
com.example.evalp3/
├── common/                          # Constantes, SoundManager, KoinInit
├── domain/
│   └── location/
│       ├── LocationRepository.kt    # Interface (contrat)
│       └── models/                  # Location, LocationDetails, Resident
├── data/
│   ├── remote/                      # HttpClient, LocationApi, CharacterApi
│   │   └── responses/               # DTOs (LocationResponse, CharacterResponse, PaginatedResponse)
│   ├── local/                       # AppDatabase, LocationDao, Mappers, DataStore
│   │   └── objects/                 # Entities Room (LocationObject)
│   └── repositories/                # LocationRepositoryImpl
└── ui/
    ├── core/                        # Navigation, Theme, Composables réutilisables
    └── screens/
        ├── locationlist/            # Screen, ViewModel, State, Action
        └── locationdetail/          # Screen, ViewModel, State
```

**Platform-specific** (`androidMain/`, `jvmMain/`) :
- Engines Ktor (CIO / Java)
- Room DatabaseBuilder
- DataStore Provider
- SoundManager (MediaPlayer / JLayer)
- Extensions de Context (Android)
- Points d'entrée (MainActivity / main.kt + DesktopApp)

## Technologies

| Technologie | Version | Rôle |
|---|---|---|
| Kotlin Multiplatform | 2.3.20 | Framework cross-platform |
| Compose Multiplatform | 1.10.3 | UI déclarative partagée |
| Ktor | 3.1.3 | Client HTTP (CIO Android, Java Desktop) |
| Room | 2.7.1 | Base de données locale |
| Koin | 4.1.0 | Injection de dépendances |
| Coil | 3.1.0 | Chargement d'images (avatars) |
| Navigation Compose | 2.9.0-alpha14 | Navigation type-safe |
| DataStore | 1.1.7 | Préférences persistantes |
| Kotlinx Serialization | 1.8.1 | Sérialisation JSON |
| JLayer | 1.0.1 | Lecture MP3 sur Desktop |

## Lancer le projet

### Prérequis

- **Android Studio** (avec le SDK Android)
- **JDK 11+** (le JBR d'Android Studio suffit)

### Android

Depuis Android Studio : lancer la configuration `composeApp` sur un émulateur ou appareil.

Ou en ligne de commande :
```bash
./gradlew :composeApp:assembleDebug
```

### Desktop

```bash
./gradlew :composeApp:run
```

> **Note Windows** : si `JAVA_HOME` n'est pas défini, utiliser :
> ```bash
> export JAVA_HOME="$LOCALAPPDATA/Programs/Android Studio/jbr"
> ```

## Choix d'architecture et arbitrages

- **Room plutôt que SQLDelight** : Room KMP est désormais stable et offre une API familière pour les développeurs Android. Le pattern DAO/Entity est plus lisible.
- **Koin plutôt que Hilt** : Koin est nativement multiplateforme, là où Hilt est limité à Android.
- **expect/actual pour le SoundManager** : chaque plateforme a ses propres APIs audio (MediaPlayer vs javax.sound/JLayer). Le contrat commun permet d'injecter et d'utiliser le son de manière transparente dans le code partagé.
- **Composables stateless séparés** : `LocationListContent` et `LocationDetailContent` sont indépendants du layout (NavHost mobile vs Row Desktop), ce qui permet de réutiliser le même code UI dans les deux contextes.
- **CSV pour les IDs résidents** : choix pragmatique pour stocker une liste d'entiers dans une colonne Room sans TypeConverter complexe.
