
📦 Microservices Application with Quarkus for Low-Latency Systems

🧾 Project Description

This project aims to implement a microservice architecture using the Quarkus framework, optimized for low-latency and high-concurrent applications. The system is a simulation of a simple e-commerce backend consisting of three main services: Customer Service, Product Service, and Shipping Service.

Each service operates independently and communicates with others via REST API over HTTP. Performance testing is conducted using Apache JMeter with up to 500 concurrent users.

---

🛠️ Tools and Dependencies Setup

| Component   | Technology                             |
|----------   |-------------------                     |
| Framework   | Quarkus (Java)                         |
| API         | RESTful API                            |
| Database    | H2 / PostgreSQL (terpisah per layanan) |
| Testing     | Apache JMeter                          |
| Deployment  | Lokal / Kontainerisasi                 |

---

🎯 Service Structure

🔒 Customer Service – Handles authentication and authorization based on JWT
📦 Product Service – Provides CRUD operations for product data management
🚚 Shipping Service – Manages product orders (accessible to authenticated users only)

Each service runs independently, communicates via REST over HTTP, and uses its own PostgreSQL database for data persistence.

Each service follows this structure:
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

🚀 How to Run the Application

1. Start each service using the command:
   ```bash
   ./mvnw quarkus:dev / mvn quarkus:dev
   ```
2. Use Postman or curl to access the following endpoints:

🔐 Authentication (Customer Service)
```http
POST /auth/login
```

📄 Product Management (Product Service)
- `GET /product` - List Produk
- `POST /product` - Add Product *(admin)*
- `PUT /product/{id}` - Update Product *(admin)*
- `DELETE /product/{id}` - Delete Product *(admin)*

📬 Ordering (Shipping Service)
```http
POST /order
```

Each request must include an Authorization: Bearer <token> header from the login response.

---

🧪 Performance Testing

- Testing was conducted using Apache JMeter:
- Threads: 10, 50, 100, and 500 users
- Ramp-up time: 1s, 5s, and 10s
- Tested Endpoints: login, product list, order

📈 Test Results:

- Microservices: Stable latency between 85ms – 2s
- Monolithic: Latency increased drastically to 10–20s
- No request failures observed in microservices

---

⚖️ Architecture Comparison

| Aspek              | Monolitik                        | Microservices                    |
|--------------------|----------------------------------|----------------------------------|
| Structure          | Unified                          | Docoutpled                       |
| Scalabiity         | Limited                          | Independent per services         |
| Latency (500 user) | 10–20 seconds                    | 85ms – 2 seconds                 |
| Failure Impact     | Entire System                    | Isolated to one service          |
| Maintenance        | Complex, Hard                    | Easy, modular                    |

---

🧩 Additional Notes

- Each service uses a separate database to ensure data isolation and consistency.
- JWT tokens are embedded in each request to enforce role-based access (@RolesAllowed).
- The structure and annotations follow best practices of REST API design with Quarkus.

---

📚 References

- Official documentation: https://quarkus.io/
- RESTI Journal 2024: Design and Implementation of Microservices with Quarkus Framework for Low-Latency Applications

---

👩‍💻 Author

Aisyah Putri Arifah
Informatics Engineering Program
Politeknik Negeri Batam
📧 ar.sam.ais@gmail.com
