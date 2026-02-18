# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Aniper** is an Android Live Wallpaper app that displays interactive animated characters on the user's home screen. Characters respond to touch input (tap, long-press, drag) with different animations and physics-based falling behavior. Users can create custom characters, download from a community market, and manage their collection.

### Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material3)
- **Architecture**: MVVM with Hilt DI
- **Database**: Room with Kotlin Serialization
- **Networking**: Retrofit 2.11.0
- **Image Loading**: Coil with GIF support
- **State Management**: Flow/StateFlow with Coroutines
- **Build Tool**: Gradle 8.7

## Project Structure

```
app/src/main/kotlin/com/aniper/app/
├── data/                    # Data layer (repositories, entities, remote/local)
│   ├── local/              # Room entities, DAOs, DataStore preferences
│   ├── remote/             # Retrofit DTOs, mock data source
│   └── repository/         # CharacterRepository, MarketRepository (data abstraction)
├── domain/                 # Business logic (models, use cases)
│   ├── model/             # Character, Motion, MarketCharacter domain models
│   └── usecase/           # 6 use cases (get, download, create, set active, etc.)
├── wallpaper/             # Live Wallpaper implementation
│   ├── AnIperWallpaperService.kt  # Main WallpaperService with engine orchestration
│   ├── engine/            # WallpaperEngine (render loop), CharacterRenderer, TouchHandler
│   ├── ai/                # CharacterBehaviorAI (state machine for animations)
│   └── physics/           # FallPhysics (gravity simulation)
├── ui/                    # Jetpack Compose UI
│   ├── theme/            # Color, Type, Theme (Material3 pastel dark palette)
│   ├── navigation/       # AnIperNavGraph (4-tab bottom nav)
│   ├── screen/           # Home, Market, Create, Settings with ViewModels
│   └── component/common/ # Reusable: AnIperButton, GifImage, LoadingIndicator
├── di/                   # Hilt dependency injection modules
├── MainActivity.kt       # Entry point, Hilt setup
└── AnIperApplication.kt # App initialization, default character seeding
```

## Architecture Notes

### Data Flow
1. **ViewModels** (HomeViewModel, MarketViewModel, etc.) use injected repositories
2. **Repositories** provide clean API boundaries - CharacterRepository (local) and MarketRepository (API)
3. **Use Cases** sit between repositories and ViewModels for reusable business logic
4. **DataStore** (via AppPreferences) manages active character ID shared between UI and WallpaperService

### Character Display Pipeline
- **HomeScreen/MarketScreen**: Display character lists with thumbnails using Coil GifImage
- **WallpaperEngine**: Runs on separate thread with Choreographer for 60fps VSync-synced rendering
- **CharacterRenderer**: Manages frame timing and Canvas drawing
- **CharacterBehaviorAI**: Updates character position/state each frame (walk, idle, grab, fall, land)
- **FallPhysics**: Applies gravity and bounce damping when character is dropped

### Touch Handling
- WallpaperService captures MotionEvents and delegates to TouchHandler
- TouchHandler distinguishes: tap (<200ms), long-press (≥200ms), drag (with movement)
- Maps to Motion enum: CLICK → tap, GRAB → long-press, FALL/LAND → release

### Mock vs Real API
- `NetworkModule` has `@Named("useMock")` flag (currently true)
- MockMarketDataSource provides 10 sample characters
- To switch to real API: set `provideUseMock()` to false and update Retrofit baseUrl in NetworkModule

## Development Rules & Workflow

### Post-Modification Build & Push Rule ⚠️
**이 규칙을 반드시 따르세요:**

모든 코드 수정 후에는 다음 순서를 반드시 실행해야 합니다:
1. **코드 수정 작업 완료**
2. **`./gradlew assembleDebug` 실행하여 빌드 진행** (Claude가 직접 실행)
3. **빌드 오류 분석 및 자동 수정** (Claude가 직접 수정)
4. **빌드 성공 확인** (오류가 없을 때까지 반복)
5. **빌드 성공 후 다음 커밋 메시지와 함께 푸시:**
   ```bash
   git add .
   git commit -m "[기능설명] 코드 수정 및 빌드 성공"
   git push origin main
   ```
6. **중요: 빌드 전에 `git status`로 추적되지 않는 파일 확인**
7. **`.env`, 토큰, API 키 등 민감한 정보는 절대 커밋하지 않기**

### 오류 해결 자동화
- Claude가 빌드를 직접 시도하므로 사용자가 오류 메시지를 복사할 필요 없음
- 빌드 오류 발생시 자동으로 분석하고 수정
- 빌드 성공시에만 GitHub에 푸시

### GitHub Repository
- **Repository**: https://github.com/fresm06/Aniper-re-
- **Branch**: main (기본 브랜치)
- **Push 전 필수 사항**: 빌드 성공 ✅ + 오류 해결 ✅

## Common Development Tasks

### Build & Run
```bash
# Debug build
./gradlew assembleDebug

# Install and run on emulator/device
./gradlew installDebug
adb shell am start -n com.aniper.app/.MainActivity

# Run tests
./gradlew test

# Build release
./gradlew assembleRelease
```

### Adding a New Screen
1. Create `ui/screen/{screenName}/` folder
2. Create `{ScreenName}ViewModel.kt` with @HiltViewModel
3. Create `{ScreenName}Screen.kt` @Composable function
4. Add route to `Screen` sealed class in `ui/navigation/AnIperNavGraph.kt`
5. Add NavHost composable route and bottom nav item

### Adding Database Fields
1. Modify `CharacterEntity` in `data/local/entity/`
2. Update `CharacterDao` with new query if needed
3. Increment `AnIperDatabase.version` (currently 1) and handle migration
4. Update corresponding domain model `Character`
5. Update repositories to map entity ↔ domain model

### Working with Default Characters
- Default characters (Fluffy, Sparky) are seeded in `AnIperApplication.onCreate()`
- Edit `createFluffyCharacter()` / `createSparkyCharacter()` to change defaults
- Only inserted on first run (checks `characterDao.getCount()`)

### Mock Data
- `MockMarketDataSource.getMockCharacters()` returns 10 hardcoded characters
- Asset URLs use `asset://` scheme (e.g., `asset://fluffy_idle`)
- Add more mock characters by extending the list in `getMockCharacters()`
- Search/filter logic in `searchCharacters()` method (filters by name, description, tags)

### Rendering Custom Characters
Currently uses placeholder asset:// URLs. To render actual images:
1. CharacterRenderer.getFrameForMotion() needs to load Drawable from URL
2. For GIF support on wallpaper (Canvas), use ImageDecoder (API 28+) or Movie (fallback)
3. Update WallpaperEngine.render() to decode and draw bitmap frames at correct animation timing

## Key Dependencies & Versions

| Dependency | Version | Usage |
|---|---|---|
| Jetpack Compose BOM | 2024.02.02 | UI framework |
| Material3 | Latest in BOM | Design system |
| Hilt | 2.50 | Dependency injection |
| Room | 2.6.1 | Local database |
| Retrofit | 2.11.0 | HTTP client |
| Coil | 2.6.0 | Image loading (GIF support via coil-gif) |
| DataStore | 1.0.0 | Preferences storage |
| Kotlin Serialization | 1.6.3 | JSON serialization |

## Important Implementation Details

### Motion Enum (7 types)
```kotlin
IDLE, WALK_LEFT, WALK_RIGHT, CLICK, GRAB, FALL, LAND
```
Each motion maps to one or more animation frames. Views display 2×4 grids showing all 7 motions.

### Touch Thresholds
- **Tap**: Motion duration < 200ms, distance < 10px → Motion.CLICK
- **Long-Press**: Duration ≥ 200ms, distance < 10px → Motion.GRAB (sustained)
- **Drag**: Duration ≥ 200ms, distance ≥ 10px → Motion.GRAB + position tracking → Motion.FALL + Motion.LAND

### DataStore Key
- Single preference: `active_character_id` (String, nullable)
- Observed by WallpaperService to dynamically switch characters
- Updated by HomeViewModel when user clicks "Summon" button

### Compose Theme
- Pastel dark palette: primary=purple (#c9a8d8), secondary=mint (#a8d8d8), tertiary=pink (#f4a8c8)
- Background: #1a1a2e (dark navy), surface: #2a2a4e (secondary)
- Text: #f0e8e8 (light), secondary text: #b0a8a8 (gray)
- Applied via `AnIperTheme()` wrapper in MainActivity

## Testing Strategy

- **Unit Tests**: VM logic, repositories, use cases in `test/` (currently not created)
- **Integration Tests**: Room DAO operations in `androidTest/`
- **Manual Testing**: Wallpaper rendering on actual device/emulator (no automated UI tests yet)

To add tests:
```bash
./gradlew test           # Run unit tests
./gradlew connectedAndroidTest  # Run instrumented tests
```

## Debugging Tips

### Wallpaper Issues
- Check logcat for WallpaperEngine errors
- Verify character images load in CoilImage in UI layer first (separate from wallpaper rendering)
- Touch events may not work in preview/emulator - test on real device

### Compose Issues
- Use `@Preview` annotations for quick iteration
- Check Material3 color scheme in Theme.kt for theme-related issues
- Verify ViewModel is injected with @HiltViewModel annotation

### Database Issues
- Room compiler generates code in `build/` - check for error messages during compilation
- If adding new TypeConverters, remember to add @TypeConverters annotation to Entity or Database
- Clear app data: `adb shell pm clear com.aniper.app`

## Performance Considerations

- **WallpaperEngine**: Runs on custom thread with Choreographer; aim for 60fps
- **GifImage in Compose**: Coil handles memory efficiently; avoid loading large images
- **Database queries**: CharacterRepository uses Flow for reactive updates; no blocking on main thread
- **Serialization**: Kotlin Serialization is zero-reflection, but avoid large nested objects in Room

## Future Enhancement Points

1. **Real backend API**: Replace MockMarketDataSource with actual Retrofit calls
2. **Pixel art rendering**: Implement built-in pixel art renderer for Fluffy/Sparky (currently uses placeholder assets)
3. **Multi-character wallpaper**: Support spawning multiple characters simultaneously
4. **Character customization**: Allow color/size adjustments via settings
5. **Audio effects**: Add sound support for character interactions
6. **Cloud sync**: Sync user characters and preferences across devices
