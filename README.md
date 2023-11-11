# Spring Core

## Overview
This project is a Spring Boot application designed to manage a training platform. It consists of three main controllers: `TraineeController`, `TrainerController`, and `TrainingController`. Each controller handles different aspects of the platform, providing a comprehensive set of APIs to interact with trainees, trainers, and training sessions.

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

## Running the Application
[Instructions on how to run and set up the project, including any prerequisites.]

## Contributing
[Guidelines for contributing to the project.]

## License
[Information about the project's license.]
