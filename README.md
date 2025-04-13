# 🎬 MoviesApp

A modern Android application to explore and search movies using the TMDB API. 
Built with **Jetpack Compose**, **Kotlin**, **MVVM** and **Clean Architecture**, it showcases scalable architecture, efficient pagination, and clean UI/UX patterns.

---

## 📱 Features

- Browse **popular movies** (paginated)
- **Search** for movies with real-time query updates
- Movie **details screen**
- Offline-first architecture using **Room** + **Remote Mediator**
- Error handling (no internet, empty states, API failures)
- Loading indicators with **shimmer effects**

---

## 🧠 Architecture

This project follows **Clean Architecture** with modular layers:


- **ViewModel** for UI state & logic
- **Paging 3** for infinite scrolling & remote + local sync
- **Hilt** for dependency injection
- **Room** + **RemoteMediator** for caching popular movies
- **Search** is handled via `SearchMoviesPagingSource` (no cache)

---

## 🛠️ Tech Stack

- 🧬 **Kotlin + Coroutines + Flow**
- 🧩 **Jetpack Compose**
- 🧱 **Room Database**
- 🚀 **Paging 3**
- 🌐 **Retrofit** for TMDB API
- 🖼️ **Glide / Coil / Landscapist**
- 📦 **Hilt** for DI

---

## 📽 App Demo

🎬 [Watch Demo Video](https://drive.google.com/file/d/1Xigz_NWKgzpcz1GAzPMKsN6nQk5m2Dll/view?usp=drive_link)

---

## 📦 Download APK

📱 [Download APK](https://drive.google.com/file/d/1_ooPmrrYjRAgrb5hciQ8YIIUqG--pWRw/view?usp=drive_link)
