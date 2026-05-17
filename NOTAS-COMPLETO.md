# Documentación Completa del Proyecto Java Spring Inventory Backend

## Controladores (3)

### 1. ClientController
**Ruta base:** `api/client`

**Métodos:**
- **POST** `/` - `create(@RequestBody Client client)`
  - Crea un nuevo cliente
  - Retorna: `ResponseEntity<Client>` con HttpStatus.OK
  - Lanza: Exception

- **PUT** `/{id}` - `update(@RequestBody Client client, @PathVariable Long id)`
  - Actualiza un cliente existente por ID
  - Retorna: `ResponseEntity<Client>` con HttpStatus.OK
  - Lanza: Exception

- **GET** `/{id}` - `findById(@PathVariable Long id)`
  - Busca un cliente por ID
  - Retorna: `ResponseEntity<Client>` con HttpStatus.OK
  - Lanza: Exception

- **GET** `/` - `findByAll()`
  - Obtiene todos los clientes
  - Retorna: `ResponseEntity<List<Client>>` con HttpStatus.OK

---

### 2. InvoiceController
**Ruta base:** `api/invoice`

**Métodos:**
- **POST** `/` - `create(@RequestBody Invoice invoice)`
  - Crea una nueva factura
  - Retorna: `ResponseEntity<Invoice>` con HttpStatus.OK
  - Lanza: Exception

- **PUT** `/{id}` - `update(@RequestBody Invoice invoice, @PathVariable Long id)`
  - Actualiza una factura existente por ID
  - Retorna: `ResponseEntity<Invoice>` con HttpStatus.OK
  - Lanza: Exception

- **GET** `/{id}` - `findById(@PathVariable Long id)`
  - Busca una factura por ID
  - Retorna: `ResponseEntity<Invoice>` con HttpStatus.OK
  - Lanza: Exception

- **GET** `/` - `findByAll()`
  - Obtiene todas las facturas
  - Retorna: `ResponseEntity<List<Invoice>>` con HttpStatus.OK

---

### 3. ProductController
**Ruta base:** `api/product`

**Métodos:**
- **POST** `/` - `create(@RequestBody Product product)`
  - Crea un nuevo producto
  - Retorna: `ResponseEntity<Product>` con HttpStatus.OK
  - Lanza: Exception

- **PUT** `/{id}` - `update(@RequestBody Product product, @PathVariable Long id)`
  - Actualiza un producto existente por ID
  - Retorna: `ResponseEntity<Product>` con HttpStatus.OK
  - Lanza: Exception

- **GET** `/{id}` - `findById(@PathVariable Long id)`
  - Busca un producto por ID
  - Retorna: `ResponseEntity<Product>` con HttpStatus.OK
  - Lanza: Exception

- **GET** `/` - `findByAll()`
  - Obtiene todos los productos
  - Retorna: `ResponseEntity<List<Product>>` con HttpStatus.OK

---

## Servicios (4)

### 1. ClientService
**Ubicación:** `service/ClientService.java`

**Métodos:**
- **create(Client newClient)** - `@Transactional`
  - Valida que el DNI contenga solo números
  - Valida que el nombre no sea nulo o vacío
  - Valida que el apellido no sea nulo o vacío
  - Verifica que no exista un cliente con el mismo DNI
  - Retorna: Client
  - Lanza: Exception (validaciones), AlreadyExistException

- **update(Client newClient, Long id)** - `@Transactional`
  - Valida que el ID sea válido (> 0)
  - Busca el cliente por ID
  - Actualiza nombre, apellido y número de documento
  - Retorna: Client
  - Lanza: Exception (ID inválido), NotFoundException

- **findById(Long id)**
  - Valida que el ID sea válido (> 0)
  - Busca el cliente por ID
  - Retorna: Client
  - Lanza: Exception (ID inválido), NotFoundException

- **findByAll()**
  - Obtiene todos los clientes
  - Retorna: List<Client>

---

### 2. InvoiceDetailsService
**Ubicación:** `service/InvoiceDetailsService.java`

**Métodos:**
- **create(InvoiceDetails newInvoiceDetails)**
  - Verifica que no exista un detalle de factura con el mismo ID
  - Retorna: InvoiceDetails
  - Lanza: AlreadyExistException

- **findByAll()**
  - Obtiene todos los detalles de facturas
  - Retorna: List<InvoiceDetails>

---

### 3. InvoiceService
**Ubicación:** `service/InvoiceService.java`

**Dependencias:**
- InvoiceRepository
- ClientRepository
- ProductRepository

**Métodos:**
- **create(Invoice newInvoice)** - `@Transactional`
  - Valida que el cliente sea obligatorio y exista
  - Valida que la factura tenga al menos un producto
  - Valida que cada línea tenga producto y cantidad > 0
  - Verifica stock suficiente para cada producto
  - Actualiza el stock de los productos (resta la cantidad vendida)
  - Calcula el total de la factura
  - Retorna: Invoice
  - Lanza: Exception (validaciones, cliente no existe, stock insuficiente)

- **update(Invoice newInvoice, Long id)** - `@Transactional`
  - Valida que el ID sea válido (> 0)
  - Busca la factura por ID
  - Actualiza cliente, total y lista de detalles
  - Retorna: Invoice
  - Lanza: Exception (ID inválido), NotFoundException

- **findById(Long id)**
  - Valida que el ID sea válido (> 0)
  - Busca la factura por ID
  - Retorna: Invoice
  - Lanza: Exception (ID inválido), NotFoundException

- **findByAll()**
  - Obtiene todas las facturas
  - Retorna: List<Invoice>

---

### 4. ProductService
**Ubicación:** `service/ProductService.java`

**Métodos:**
- **create(Product newProduct)** - `@Transactional`
  - Valida que el código no sea nulo o vacío
  - Valida que el stock no sea negativo
  - Valida que el precio sea mayor a 0
  - Verifica que no exista un producto con el mismo código
  - Retorna: Product
  - Lanza: Exception (validaciones), AlreadyExistException

- **update(Product newProduct, Long id)** - `@Transactional`
  - Valida que el ID sea válido (> 0)
  - Busca el producto por ID
  - Actualiza descripción, código, stock y precio
  - Retorna: Product
  - Lanza: Exception (ID inválido), NotFoundException

- **findById(Long id)**
  - Valida que el ID sea válido (> 0)
  - Busca el producto por ID
  - Retorna: Product
  - Lanza: Exception (ID inválido), NotFoundException

- **findByAll()**
  - Obtiene todos los productos
  - Retorna: List<Product>

---

## Resumen de Estructura

**Controladores:** 3 (Client, Invoice, Product)
**Servicios:** 4 (Client, InvoiceDetails, Invoice, Product)

Todos los controladores siguen el patrón REST estándar con operaciones CRUD:
- POST / - Crear
- PUT /{id} - Actualizar
- GET /{id} - Buscar por ID
- GET / - Listar todos

Todos los servicios implementan validaciones de negocio y manejo de excepciones personalizado.
