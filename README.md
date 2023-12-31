# Gym CRM System

## Overview
This project is a Spring Boot application designed to manage a training platform. It consists of three main controllers: `TraineeController`, `TrainerController`, and `TrainingController`. Each controller handles different aspects of the platform, providing a comprehensive set of APIs to interact with trainees, trainers, and training sessions.
## DB schema
![DB schema](https://github.com/komilovqudratjon/spring-core-task-epam/blob/main/data/images/db.png?raw=true)


## Swagger UI
![swagger ](https://github.com/komilovqudratjon/spring-core-task-epam/blob/main/data/images/swagger.png?raw=true)

## link API Documentation
[Swagger link](http://localhost:1111/api/swagger-ui.html#/)

## Controllers

### 1. TraineeController
This controller manages trainees' data. It offers CRUD operations and listing with filtering capabilities.

#### Endpoints
- `POST /api/v1/trainees`: Create or update a trainee.
- `GET /api/v1/trainees/{id}`: Retrieve a specific trainee by ID.
- `DELETE /api/v1/trainees/{id}`: Delete a trainee by ID.
- `GET /api/v1/trainees`: Get all trainees with optional filtering by date of birth and address.

### 2. TrainerController
This controller is responsible for handling trainers' information.

#### Endpoints
- `POST /api/v1/trainers`: Create a trainer.
- `GET /api/v1/trainers/{id}`: Retrieve a specific trainer by ID.
- `GET /api/v1/trainers`: Get all trainers.

### 3. TrainingController
This controller manages the training sessions, including creating, retrieving, and listing trainings with advanced filtering options.

#### Endpoints
- `POST /api/v1/trainings`: Create a training session.
- `GET /api/v1/trainings/{id}`: Get a specific training session by ID.
- `GET /api/v1/trainings`: List all training sessions.
- `GET /api/v1/trainings/search`: Advanced search for training sessions with various filters like trainee ID, trainer ID, training name, type, date, and duration.

## Health Check Endpoint

Our application provides a health check endpoint to monitor the status and health of the application in real-time. This can be particularly useful during development and in production for monitoring and alerting.

### Accessing the Health Check

To access the health check, use the following URL:

[http://localhost:1111/api/actuator/health](http://localhost:1111/api/actuator/health)

This will return a JSON response with the health status of the application.

### Expected Response

When the application is running correctly, you should receive a response similar to:

```json
{
  "status": "UP"
}
```

# Prometheus Actuator Endpoint

## Overview
This project is configured with Spring Boot Actuator, which provides several management endpoints. One of these endpoints is for Prometheus metrics.

## Accessing Prometheus Metrics
You can access the Prometheus metrics at the following URL:

[http://localhost:1111/api/actuator/prometheus](http://localhost:1111/api/actuator/prometheus)

This endpoint exposes various metrics in a format that Prometheus can scrape. These metrics are useful for monitoring and alerting purposes in a production environment.

## Usage
To view the metrics, simply navigate to the above URL in your browser, or use a tool like `curl`:

```bash
curl http://localhost:1111/api/actuator/prometheus
```

### Installing

1. Clone the repository:

   ```bash
   git clone https://github.com/komilovqudratjon/spring-core-task-epam.git
   ```

2. Navigate to the project directory:

   ```bash
   cd gym-crm-system
   ```

3. Build the project using Maven:

   ```bash
   mvn clean install
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```