# 🎨 Mini Éditeur de Pixel Art

Ce projet est un petit éditeur de pixel art développé en Java avec Swing, permettant de créer, sauvegarder, charger et exporter des dessins pixelisés. L’application intègre une base de données pour stocker les dessins et leurs auteurs, et propose plusieurs fonctionnalités ergonomiques pour une expérience utilisateur fluide.

---

## 🚀 Fonctionnalités principales

- 🧱 Grille de dessin dynamique (choix de la résolution 16x16, 32x32, 64x64)
- 🎨 Sélecteur de couleur (color picker intégré)
- 👤 Attribution d’un auteur au dessin
- 💾 Sauvegarde en base de données avec persistence complète
- 📂 Chargement d’un dessin existant depuis la base
- 🔍 Prévisualisation du dessin sélectionné
- 🗑️ Suppression d’un dessin
- ❎ Effacement complet de la grille
- 🖼️ Export du dessin au format PNG

---

## 🗂️ Structure du projet

- `src/` – Code Java organisé en MVC
- `src.model` – Classes métier : `Dessin`, `Pixel`, `Auteur`
- `src.dao` – Accès base de données avec JDBC
- `src.view` – Interfaces Swing : `PixelArtFrame`, `ChargementDialog`, etc.
- `src.utils` – Méthodes utilitaires (génération d’images, conversions)

---

## 🧰 Technologies utilisées

- Java 17+
- Swing
- JDBC (MySQL)
- Maven (gestion des dépendances)

---

## 🛠️ Lancer l'application

1. Importer le projet dans un IDE Java (Eclipse, IntelliJ, NetBeans…)
2. Configurer votre base MySQL et importer le fichier `pixel_art_dump.sql`
3. Mettre à jour le fichier de configuration de la BDD (URL, user, password)
4. Compiler et exécuter la classe `Main`

---

## 🗃️ Base de données

Le projet utilise une base contenant les tables :
- `dessin` – Stocke les métadonnées d’un dessin
- `pixel` – Coordonnées et couleur des pixels
- `auteur` – Nom des auteurs

---

## 📸 Aperçu

- Création intuitive d'un dessin
- Export PNG
- Chargement avec miniature
- Gestion de plusieurs auteurs

---

## ✍️ Auteur

Projet réalisé dans le cadre du module NFA019.  
Développé par Schentuleit Victor

---

## 📝 Licence

Projet académique – libre d’usage dans un cadre éducatif.
