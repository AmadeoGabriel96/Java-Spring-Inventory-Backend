# NOTAS-ARQUITECTURA.md

## Controladores (Controllers)
1. **ClientController**
   - **Endpoints**:
     - `POST /api/client`: Crea un cliente.
     - `PUT /api/client/{id}`: Actualiza un cliente por su ID.
     - `GET /api/client/{id}`: Obtiene un cliente por su ID.
     - `GET /api/client`: Obtiene todos los clientes.

## Servicios (Services)
1. **ClientService**
   - **Métodos**:
     - `create(Client client)`: Crea un nuevo cliente.
     - `update(Client client, Long id)`: Actualiza un cliente existente por su ID.
     - `findById(Long id)`: Obtiene un cliente por su ID.
     - `findByAll()`: Obtiene todos los clientes.

## Modelos (Entities)
1. **Client**
   - **Atributos**:
     - `id` (Long)
     - `nombre` (String)
     - `email` (String)
     - ...