# CarBooking (Android) – Firestore Version

Eine einfache Android-App (Kotlin) zum Anzeigen von Autos und Erstellen/Verwalten von Buchungen.  
Daten werden aus **Firebase Cloud Firestore** geladen und Buchungen werden dort gespeichert.

## Features
- ✅ Liste aller Autos (RecyclerView) aus Firestore (`cars`)
- ✅ Detailansicht eines Autos (Laden per `carId`)
- ✅ Buchung anlegen (Name + Start/Enddatum) → Speicherung in Firestore (`bookings`)
- ✅ Buchungen anzeigen (RecyclerView) inkl. Auto-Infos (Join über `carId` mit lokalem Cache)
- ✅ Buchung löschen per longklick (Storno) → Löschen in Firestore

## Tech Stack
- Kotlin
- AndroidX (RecyclerView)
- Firebase Cloud Firestore

## Firestore Datenmodell

### Collection: `cars` (Document-ID = `carId`)
Felder (Beispiel):
- `brand` (string)
- `model` (string)
- `pricePerDay` (number)
- `location` (string)
- `available` (boolean)
- `image` (string) → Name eines Drawables, z.B. `"golf"`
- `seats` (number)
- `transmission` (string)
- `fuel` (string)
- `description` (string)

### Collection: `bookings` (Document-ID auto)
Felder:
- `carId` (string)
- `customerName` (string)
- `startDate` (string, z.B. `2026-02-10`)
- `endDate` (string, z.B. `2026-02-12`)
- `createdAt` (timestamp, `serverTimestamp()`)

## Setup (lokal ausführen)

1. Repository klonen und in Android Studio öffnen
2. Firebase-Projekt erstellen (oder bestehendes nutzen) und Firestore aktivieren
3. Android App in Firebase registrieren (Package Name muss passen)
4. `google-services.json` herunterladen und nach `app/google-services.json` legen
5. Gradle Sync ausführen
6. In Firestore Testdaten anlegen (`cars` Collection mit mehreren Dokumenten)
7. App starten

## Hinweise
- Bilder werden aktuell aus `res/drawable` geladen. Das Feld `image` in Firestore muss dem Drawable-Namen entsprechen (ohne Endung).
- Datumseingaben sind aktuell einfache Textfelder (`yyyy-MM-dd`). DatePicker/Validierung kann später ergänzt werden.

## Projektstruktur (grob)
- `CarListActivity` – Auto-Liste
- `CarDetailActivity` – Auto-Details
- `CreateBookingActivity` – Buchung erstellen
- `BookingsActivity` – Buchungen anzeigen / löschen
- `DataLoader` – zentrale Daten-Schicht (Firestore-Zugriff)
