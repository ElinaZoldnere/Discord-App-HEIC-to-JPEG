# Discord Bot (App) for HEIC Image Conversion
This repository contains a Discord App designed for automated conversion of HEIC format image files 
to JPEG images within Discord text channels.

## Overview

On Discord, HEIC files are treated as regular files rather than images, which prevents them from 
being previewed and embedded in the text like other image formats. This can be inconvenient and
disturbing, so I developed a custom solution to enhance the user experience.

In this implementation, when a HEIC format attachment is uploaded in a text channel, the app 
converts it to a JPEG image. The bot then posts the converted image in the same text channel.

The project is developed with Java Spring Boot and built with Gradle. 
The image conversion service is separated from the app, developed as a Google Cloud Function written
in Python with Flask and using the Pillow framework for image processing. It communicates with the 
app through HTTP requests.

## Project Structure

- `.github/workflows/`: Contains GitHub Actions workflows for continuous integration.
- `cloud-function/`: Contains the Google Cloud Function script and its dependencies.
    - `main.py`: The Cloud Function script.
    - `requirements.txt`: The dependencies required for the Cloud Function.
- `src/`: Contains the Java source code.
- `build.gradle`: The Gradle build file.
- `settings.gradle`: The Gradle settings file.

## Environment Variables
Sensitive data for the project is specified as environment variables. In the Google Cloud Function, 
they are set directly during initial deployment. In the Spring Boot application, they are loaded 
from a `.env` file. The `application.properties` file contains the loading configuration and 
variable placeholders for reference.

