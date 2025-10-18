# 🛍️ Tienda Online — Spring Boot + JPA + H2

Proyecto de ejemplo que implementa una **tienda en línea** con operaciones CRUD completas y relaciones entre entidades usando **Spring Boot**, **Spring Data JPA** y **base de datos en memoria H2**.

---

## 🚀 Tecnologías usadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (en memoria)
- Maven
- REST API con JSON
- Manejo de errores global con `@RestControllerAdvice`

---

## 🧩 Dominio modelado

| Entidad | Relaciones principales |
|----------|------------------------|
| **Cliente** | 1:1 con **Dirección**, 1:N con **Pedido** |
| **Dirección** | Posee FK hacia **Cliente** |
| **Pedido** | N:1 con **Cliente**, N:M (por medio de **ItemPedido**) con **Producto** |
| **ItemPedido** | Entidad intermedia entre **Pedido** y **Producto** |
| **Producto** | N:M con **Categoría** (puro con `@JoinTable`) |
| **Categoría** | M:N con **Producto** |

---

## ⚙️ Cómo ejecutar el proyecto

### 1️⃣ Requisitos previos
- Tener **Java 17** o superior instalado  
- Tener **Maven 3.9+** instalado o usar el wrapper (`./mvnw`)

### 2️⃣ Ejecutar el proyecto

bash
mvn spring-boot:run
El   servidor se levantará en:
http://localhost:8080
🗃️ Base de datos H2

Se usa H2 en memoria por defecto, así que no requiere instalación ni configuración.

Puedes acceder al panel web en:

http://localhost:8080/h2-console


Credenciales por defecto:

JDBC URL: jdbc:h2:mem:tienda

Usuario: sa

Password: (vacío)

🧠 Relaciones principales

Cliente ↔ Dirección — @OneToOne con cascade=ALL, orphanRemoval=true

Cliente ↔ Pedido — @OneToMany(mappedBy="cliente")

Pedido ↔ Producto — relación N:M por entidad intermedia ItemPedido

Producto ↔ Categoría — relación N:M directa con @JoinTable

🧪 Endpoints principales
👤 Clientes
Crear cliente + dirección
POST /api/clientes
Content-Type: application/json

{
  "nombre": "Juan Perez",
  "email": "juan@correo.com",
  "direccion": {
    "calle": "Calle 123",
    "ciudad": "Bogotá",
    "pais": "Colombia",
    "zip": "110111"
  }
}

Obtener cliente
GET /api/clientes/1

🏷️ Categorías
Crear categoría
POST /api/categorias
{
  "nombre": "Electrónica"
}

📦 Productos
Crear producto y asociar categorías
POST /api/productos
{
  "nombre": "Mouse inalámbrico",
  "precio": 25.5,
  "stock": 10,
  "categorias": ["Electrónica"]
}

Listar productos por categoría
GET /api/productos/categoria/1

🧾 Pedidos
Crear pedido con items
POST /api/pedidos
{
  "clienteId": 1,
  "items": [
    {"productoId": 1, "cantidad": 2},
    {"productoId": 2, "cantidad": 1}
  ]
}


✅ Verifica que el stock de cada producto disminuya automáticamente.
❌ Si se repite un mismo producto dentro del pedido, lanza error.

Cambiar estado del pedido
PUT /api/pedidos/1/estado?estado=PAGADO


Estados disponibles:
PENDIENTE, PAGADO, ENVIADO, ENTREGADO, CANCELADO

⚠️ Validaciones importantes

No se puede agregar el mismo producto dos veces al mismo pedido.

Si un producto no tiene suficiente stock, se lanza error.

Al eliminar un cliente, se eliminan sus pedidos (por orphanRemoval=true).

Categorías con nombre repetido no se permiten (unique constraint).

🧰 Estructura del proyecto
src/
 ├── main/
 │   ├── java/com/example/tienda/
 │   │   ├── controller/
 │   │   ├── dto/
 │   │   ├── entity/
 │   │   ├── exception/
 │   │   ├── repository/
 │   │   ├── service/
 │   │   └── TiendaApplication.java
 │   └── resources/
 │       ├── application.properties
 │       └── data.sql (opcional)
 └── test/ (pendiente)
 
## COLECCIÓN POSTMAN/CURL PARA DEMOSTRAR CASOS.
<img width="921" height="375" alt="image" src="https://github.com/user-attachments/assets/d49bd988-a022-41b0-ab57-995711c3b4a4" />
<img width="921" height="350" alt="image" src="https://github.com/user-attachments/assets/e501668e-2af9-42e3-a6bf-594e48b21596" />
<img width="921" height="363" alt="image" src="https://github.com/user-attachments/assets/32a9400d-db14-4a7a-9b4f-04d7c151efc5" />
<img width="921" height="374" alt="image" src="https://github.com/user-attachments/assets/c4fdcbda-4f6e-42b4-b061-fb7fc1ab54d1" />
<img width="921" height="367" alt="image" src="https://github.com/user-attachments/assets/5323a44e-ac1e-4e8c-a723-11f322128314" />
<img width="921" height="407" alt="image" src="https://github.com/user-attachments/assets/d59c3a3c-b601-4670-a0c9-afd8e434898d" />
## CAPTURAS H2 CONSOLE O LOGS DE SQL EVIDENCIANDO RELACIONES
<img width="409" height="146" alt="image" src="https://github.com/user-attachments/assets/876b794c-8fba-4446-94b3-0196966080a5" />
<img width="221" height="126" alt="image" src="https://github.com/user-attachments/assets/88db8543-8ab4-4acd-ad08-522825cd855f" />
<img width="330" height="170" alt="image" src="https://github.com/user-attachments/assets/66bda146-7e89-47ee-934d-e2e3ca6bcb5c" />
<img width="466" height="205" alt="image" src="https://github.com/user-attachments/assets/26f64564-5d2d-43fd-9dfb-f64123a2f29f" />
<img width="537" height="173" alt="image" src="https://github.com/user-attachments/assets/6286afda-567d-4d1f-928f-fc31d7ff9a06" />











