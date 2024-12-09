# Generador de Contraseñas Seguras - Kata6

Este proyecto es una **API para generar contraseñas seguras** según los parámetros especificados por el usuario. Está diseñado para proporcionar contraseñas que cumplan con criterios personalizados de longitud y uso de caracteres especiales.

## Funcionalidad de la API

La API permite generar contraseñas dinámicas según dos parámetros principales:

- **`length`**: Longitud de la contraseña (número entero positivo).
- **`special`**: Indicador de inclusión de caracteres especiales (`1` para incluir, `0` para excluir).

### Endpoint principal

La API expone el siguiente endpoint:
GET http://localhost:8080/api/password


### Ejemplo de uso

#### Solicitud para generar una contraseña de 10 caracteres sin caracteres especiales:
GET http://localhost:8080/api/password?length=10&special=0
#### Respuesta esperada:
```json
{
    "password": "aBcD1234Ef"
}
```


## Principios de Comandos y Adaptadores en el Proyecto

Este proyecto implementa un diseño basado en **Comandos** y **Adaptadores**, que permite una separación clara entre las responsabilidades de procesamiento de solicitudes, generación de respuestas y ejecución de la lógica de negocio.

### Estructura de las Clases

1. **Adaptadores**: 
   - **`SpringRequestAdapter`**: Adapta la solicitud HTTP para extraer parámetros de consulta como `length` y `special`. 
   - **`SpringResponseAdapter`**: Construye una respuesta en formato JSON, encapsulando el cuerpo de la respuesta.

2. **Comando**: 
   - **`CustomPasswordCommand`**: Contiene la lógica de negocio para generar la contraseña segura. Implementa el método `execute()` para realizar la operación principal.

3. **Fábrica de Comandos**:
   - **`CommandFactory`**: Permite registrar y recuperar comandos según el path de la solicitud. Facilita la extensibilidad de la API al agregar nuevos endpoints y comandos.

4. **Controlador**:
   - **`CommandController`**: Maneja las solicitudes HTTP y delega el procesamiento al comando adecuado, recuperado a través de la fábrica de comandos.

### Flujo de Ejecución

1. **Solicitud HTTP**:
   - El cliente realiza una solicitud al endpoint, por ejemplo:  
     ```
     GET http://localhost:8080/api/password?length=12&special=1
     ```

2. **Adaptación de la Solicitud**:
   - El controlador utiliza `SpringRequestAdapter` para extraer los parámetros de la solicitud.

3. **Ejecución del Comando**:
   - El controlador obtiene un comando apropiado desde `CommandFactory` basado en el path (`/api/password`).
   - Se ejecuta el comando (`CustomPasswordCommand`), que genera la contraseña según los parámetros proporcionados.

4. **Adaptación de la Respuesta**:
   - El comando utiliza `SpringResponseAdapter` para construir la respuesta en formato JSON.

5. **Respuesta al Cliente**:
   - El controlador envía la respuesta generada al cliente, por ejemplo:
     ```json
     {
         "password": "Xy7!@hGd92"
     }
     ```

### Código de Ejemplo

#### **Adaptadores**

- `SpringRequestAdapter`:
  - Extrae parámetros de consulta como `length` y `special`.
  - Simplifica la obtención de parámetros booleanos con valores predeterminados.

- `SpringResponseAdapter`:
  - Encapsula el cuerpo de la respuesta generada.

#### **Comando: CustomPasswordCommand**

Este comando incluye la lógica para generar contraseñas utilizando un conjunto de caracteres base y opcionalmente caracteres especiales. El comando asegura que las contraseñas sean seguras mediante el uso de `SecureRandom`.

#### **Fábrica de Comandos**

La fábrica permite registrar comandos para distintos endpoints y simplifica la asociación de lógica de negocio a rutas específicas. Esto mejora la modularidad y escalabilidad de la API.

#### **Controlador**

El controlador utiliza los adaptadores para interactuar con las solicitudes y respuestas, delegando la lógica de negocio al comando correspondiente. Este diseño asegura que el controlador permanezca limpio y enfocado.

---

## Extensibilidad del Proyecto

Gracias al uso de principios de comandos y adaptadores, este proyecto es fácilmente extensible. Para agregar nuevos endpoints:

1. Crea un nuevo comando que implemente la interfaz `Command`.
2. Registra el comando en `CommandFactory`.
3. Define el comportamiento específico del comando en su método `execute()`.

Con este enfoque, es posible ampliar las capacidades de la API sin afectar el código existente.

