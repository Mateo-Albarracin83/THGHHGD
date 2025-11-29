# ALPESCAB Transportation Services

## Overview
ALPESCAB is a transportation service application that allows users to register new cities for transportation services. This project is built using Java Spring Boot and utilizes SQL for database interactions.

## Setup Instructions

1. **Correr la application**:
   Primero se accede a APP y luego a alpescab-transportation.
   ```
   cd APP/alpescab-transportation
   ```
   Y para correr la página de AlpesCab se realiza con el siguiente comando.
   ```
   ./mvnw spring-boot:run
   ```
   Para revisar algún cambio realizado, toca finalizar y volver a inicializar la aplicación. La cual se realiza con Ctrl + C y volver a ejecutar el comando en la Terminal. 

2. **Access the API**:
   Para ingresar a la aplicación, se pega L`localhost:8080/alpescab/` en el navegador. 

3. **Contribución AlpesCab**
   Al finalizar una modificación/ adición al código de la aplicación, hacer Pull-request.

## Database
The application uses an SQL database for storing city information. The `data.sql` file can be used to initialize the database with sample data.

## Testing
Unit tests are included in the `src/test/java/com/alpescab` directory. You can run the tests using:
```
./mvnw test
```

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request for any changes or improvements.