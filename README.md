
📦 Desain dan Impelemntasi Microservices dengan Framework Quakrus untuk Aplikasi Low-Latency

🧾 Deskripsi Proyek

Proyek ini bertujuan untuk mengimplementasikan arsitektur microservice menggunakan framework Quarkus, yang dioptimalkan untuk aplikasi berlatensi rendah dan bersifat highly-concurrent. Sistem ini merupakan simulasi backend e-commerce sederhana dengan tiga layanan utama: Customer Service, Product Service, dan Shipping Service.

Setiap layanan berjalan secara independen dan saling terhubung menggunakan REST API melalui protokol HTTP. Pengujian performa dilakukan menggunakan Apache JMeter dengan beban pengguna hingga 500 user secara simultan.

---

🛠️ Teknologi yang Digunakan

| Komponen    | Teknologi                              |
|----------   |-------------------                     |
| Framework   | Quarkus (Java)                         |
| API         | RESTful API                            |
| Database    | H2 / PostgreSQL (terpisah per layanan) |
| Testing     | Apache JMeter                          |
| Deployment  | Lokal / Kontainerisasi                 |

---

🎯 Struktur layanan

🔒 Customer Service – Menangani proses autentikasi dan otorisasi berbasis JWT.
📦 Product Service – Menyediakan operasi CRUD untuk pengelolaan data produk.
🚚 Shipping Service – Mengelola pemesanan produk.

Setiap layanan memiliki struktur:
```
src/
 └── main/
     └── java/
         ├── dto/
         ├── entity/
         ├── repository/
         ├── resource/
         ├── constant/
         └── client/ (khusus Shipping Service)
```

---

🚀 Cara Menjalankan Aplikasi

1. Jalankan masing-masing service dengan perintah:
   ```bash
   ./mvnw quarkus:dev / mvn quarkus:dev
   ```
2. Gunakan Postman atau curl untuk mengakses endpoint berikut:

🔐 Autentikasi (Customer Service)
```http
POST /auth/login
```

 📄 Produk (Product Service)
- `GET /product` - List Produk
- `POST /product` - Tambah Produk *(admin)*
- `PUT /product/{id}` - Edit Produk *(admin)*
- `DELETE /product/{id}` - Hapus Produk *(admin)*

📬 Pemesanan (Shipping Service)
```http
POST /order
```

Setiap request menyertakan `Authorization: Bearer <token>` dari hasil login.

---

🧪 Pengujian Performa

Pengujian dilakukan menggunakan Apache JMeter:
- Thread: 10 hingga 500 user
- Ramp-up: 1s hingga 10s
- Endpoint diuji: login, get product, order

📈 Hasil Uji:
- Microservices: Latency stabil 85ms – 2s
- Monolitik: Latency naik drastis hingga 10–20s
- Tidak ditemukan request failure pada microservices

---

⚖️ Perbandingan Arsitektur

| Aspek              | Monolitik                        | Microservices                     |
|--------------------|----------------------------------|-----------------------------------|
| Struktur           | Terpadu                          | Terpisah                          |
| Skalabilitas       | Terbatas                         | Independen per layanan            |
| Latency (500 user) | 10–20 detik                      | 85ms – 2 detik                    |
| Dampak Kegagalan   | Sistem total                     | Isolated ke satu layanan          |
| Maintenance        | Sulit, kompleks                  | Mudah, modular                    |

---

🧩 Catatan Tambahan

- Setiap layanan menggunakan database terpisah untuk memastikan isolasi dan konsistensi data.
- Token JWT disematkan ke setiap permintaan untuk kontrol akses berdasarkan peran (`@RolesAllowed`).
- Struktur dan anotasi mengikuti praktik terbaik REST API dan Quarkus.

---

📚 Referensi

- Dokumentasi resmi [https://quarkus.io/](https://quarkus.io/)
- Jurnal RESTI 2024: *Desain dan Implementasi Microservices dengan Framework Quarkus untuk Aplikasi Low-Latency*

---

👩‍💻 Penulis

**Aisyah Putri Arifah**  
Program Studi Teknik Informatika  
Politeknik Negeri Batam  
📧 ar.sam.ais@gmail.com
