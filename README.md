# Discord Bot (App) for HEIC Image Conversion
This repository contains a Discord App designed for automated conversion of HEIC format image files 
to JPEG images within Discord text channels.

## Overview

On Discord, HEIC files are treated as regular files rather than images, which prevents them from 
being previewed and embedded in the text like other image formats. This can be inconvenient and
annoying, so I developed a custom solution to enhance the user experience.

In this implementation, when a HEIC format attachment is uploaded in a text channel, the app 
converts it to a JPEG image. The bot then posts the converted image in the same text channel.

The project is developed with Java Spring Boot using Java Discord API and built with Gradle. 
The app is deployed on Google App Engine.

The image conversion service is separated from the app, developed as a Google Cloud Function written
in Python with Flask and using Pillow for image processing. It communicates with the 
app via HTTP requests. Runtime Python 3.11.

## Project Structure

- `.github/workflows/`: GitHub Actions workflows for continuous integration.
- `cloud-function/`: The Google Cloud Function script and its dependencies.
    - `main.py`: The Cloud Function script.
    - `requirements.txt`: Dependencies for the Cloud Function.
- `documentation/`: C4 model diagrams.
- `src/`: The Java source code.
    - `main/java/com/discordbot`: Production code.
        - `config/`: Configuration classes.
          - `AppConfig.java`: Initializes OkHttpClient as a Spring Bean.
          - `JdaInitializer.java`: Initializes JDA for a specific Discord bot using a token
        - `controller/HealthCheckRestController.java`: Responds to health check requests.
        - `event/`: Event listeners.
          - `EventListener.java`: Listens for Discord events.
          - `EventListenerInitializer.java`: Sets up event listeners.
        - `service/`: Core logic implementation.
          - `DiscordService.java`: Handles image conversion.
          - `FileService.java`: Manages file operations.
          - `HttpService.java`: Handles HTTP requests.
          - `ProcessConversionToJpeg.java`: Processes image conversion by delegating the task to the cloud function.
          - `DiscordBotApplication.java`: Main class to run the Spring Boot application.
    - `resources/`: Resource files.
        - `application.properties`: Spring Boot configuration.
        - `logback-spring.xml`: Logging configuration.
    - `test`: Unit and integration tests.
- `.gitattributes`: Defines file attributes for Git.
- `.gitignore`: Specifies files ignored by Git.
- `build.gradle`: The Gradle build file.
- `gradlew`: Gradle wrapper script (Unix).
- `gradlew.bat`: Gradle wrapper script (Windows).
- `README.md`: Project documentation.
- `settings.gradle`: Gradle settings.

## Deployment
The deployment configuration for Google App Engine is specified in the app.yaml file. Manual instance 
scaling is used to ensure continuous operation, as there is no direct traffic to the app (the app 
does not have its own endpoints to serve). Google App Engine performs periodic health checks to
assess the app's liveness, so a REST controller is implemented to respond to these health checks.

## Sensitive Data Handling

Sensitive data for the project is specified as environment variables. The handling of these variables 
is as follows:

- **Google Cloud Function:**
  - Environment variables are set directly during the initial deployment process.

- **Spring Boot Application:**
  - Local Development:
    - Environment variables are loaded from a `.env` file. The `application.properties` file contains 
    the configuration to load these variables (if present), and placeholders for reference.
  - Deployment:
    - Environment variables are specified in the `app.yaml` file.

## Container diagram
![C4_Container_view](documentation/c4_diagrams/C4_Container_view.png)