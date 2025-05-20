# 🍽️ RecipeApp
A cross-platform recipe application built using **Jetpack Compose Multiplatform**, allowing users to browse, search, and save their favorite recipes. Users can view recipe details, add them to favorites, and log in/out seamlessly across platforms.

---

## 🔧 Features

- ✅ Three main tabs:
  - **Home**: Browse and search recipes.
  - **Favorites**: View saved favorite recipes (requires login).
  - **Profile**: Login/Logout functionality.
- 🔍 Search recipes from the Home screen.
- 📝 Click on any recipe to see full details.
- ❤️ Save recipes to Favorites (persisted via local database).
- 🔐 Authentication flow:
  - If user is not logged in, a **Login BottomSheet** appears when trying to save a favorite.
- 🌐 Uses [TheMealDB API](https://www.themealdb.com/api.php) for fetching recipe data.

---

## 🛠️ Tech Stack

- **UI Framework**: Jetpack Compose Multiplatform
- **Data Layer**:
  - [SQLDelight](https://cashapp.github.io/sqldelight/) for local database persistence
- **Networking**: Ktor or simple HTTP client depending on platform
- **Dependency Injection**: Koin
- **Settings & Preferences**: Multiplatform Settings
- **Routing/Navigating**: Custom navigation without `WASMJS`, using `Js` as target
- **Authentication**: Simple login/logout logic with session handling

---

## 📱 UI Overview

### Tabs
- **HomeAscreen**
  - List of recipes fetched from TheMealDB API
  - Search bar to filter recipes by name
- **FavoritesScreen**
  - List of saved favorite recipes
  - Requires login to access
- **ProfileScreen**
  - Shows login status
  - Option to log in or out

### Navigation Flow
1. From Home tab, click a recipe to navigate to Details Screen.
2. In Details Screen, tap "Add to Favorites":
   - If user is logged in → Recipe is saved.
   - If user is NOT logged in → A bottom sheet prompts for login.

---

## 🌐 API Used

This app uses [TheMealDB API](https://www.themealdb.com/api.php) to fetch recipe data.

- Base URL: `https://www.themealdb.com/api/json/v1/1/`
- Endpoints used:
  - `search.php?s=` – For searching recipes
  - `lookup.php?i=` – For getting details of a specific recipe

---
