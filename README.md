# 🎬 Movie Reservation System

A **backend system** for managing movie reservations, built with **Spring Boot**, **MySQL**, **Spring Security (JWT)**, and **JPA/Hibernate**.  
The system allows users to sign up, log in, browse movies, reserve seats for specific showtimes, and manage their reservations, while admins manage movies, showtimes, and reporting.

---

## 🚀 Features

### 🔑 Authentication & Authorization
- User sign-up and login with **JWT authentication**.
- Role-based access control:
  - **Admin**: manage movies, showtimes, users, and reporting.
  - **User**: browse movies, reserve seats, manage reservations.

### 🎥 Movie & Showtime Management
- Admins can **add, update, delete** movies.
- Movies include:
  - Title, description, poster image, genre.
- Each movie can have **multiple showtimes**.

### 🎟️ Reservation Management
- Users can:
  - Browse movies & showtimes for a specific date.
  - Reserve seats (with seat selection).
  - View and cancel their **upcoming reservations**.
- Admins can:
  - View all reservations.
  - Monitor seat capacity and revenue.

### 📊 Reporting
- Generate reports on:
  - Reservation statistics.
  - Occupancy per showtime.
  - Revenue.

---

## 🛠️ Tech Stack

- **Backend:** Spring Boot (MVC), Spring Security (JWT), JPA/Hibernate  
- **Database:** MySQL  
- **Build Tool:** Maven  
- **Testing/Debugging:** Postman  

---

## 🗂️ Data Model (Entities)

- **User** – holds authentication info, roles (Admin/User).  
- **Movie** – title, description, genre, poster.  
- **Showtime** – linked to a Movie, includes date & time.  
- **Seat** – belongs to a showtime, reserved by users.  
- **Reservation** – links User ↔ Seats ↔ Showtime.  

---

## ⚙️ Setup Instructions

### 1. Clone Repository
```bash
git clone https://github.com/mohanadalaa/Movie-Reservation-System.git
cd Movie-Reservation-System
