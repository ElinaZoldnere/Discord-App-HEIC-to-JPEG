workspace {

    model {
        user = person "User" "a user of Discord text channel"

        discord = softwareSystem "Discord" "existing platform Discord" "External"
        appEngine = softwareSystem "Google App Engine" "existing deployment platform" "External"

        application = softwareSystem "Application" "Spring Boot JDA application for image conversion HEIC to JPEG" {
            botApp = container "Bot Application" "handles main logic and interactions with Discord" "Java and Spring Boot" {
                initializer = component "JDA initalizer" "initalizes a Discord bot" "Java and Spring Boot"
                listener = component "Event Listener" "listens for relevant events in Discord" "Java and Spring Boot"
                imageService = component "Image Service" "handles image file transfer and processing" "Java and Spring Boot"
                controller = component "Health Check Controller" "responds to Google App Enginge health checks" "Java and Spring Boot"
            }
            cloudFunction = container "Cloud Function" "converts images HEIC to JPEG" "Python and Flask"
        }

        user -> discord "posts HEIC image in the Discord text channel"
        listener -> discord "listens for relevant events"
        discord -> listener "forwards HEIC image to application"
        listener -> imageService "forwards HEIC image for processing"
        imageService -> cloudFunction "sends HEIC image for conversion"
        cloudFunction -> imageService "returns converted JPEG"
        imageService -> discord "sends converted JPEG back to Discord"

        // additional relationship definition for Context and Component view
        application -> discord "sends converted JPEG back to Discord"
        botApp -> discord "sends converted JPEG back to Discord"

        discord -> user "bot posts JPEG image in text channel and Discord displays it"

        initializer -> discord "initializes Discord bot"
        appEngine -> controller "performs health checks via HTTP request"
    }

    views {
        systemContext application "Application" {
            include *
            include user
            exclude appEngine
            autoLayout lr
        }

        container application "Containers" {
            include *
            include user
            exclude appEngine
            autoLayout lr
        }

        component botApp "Components" {
            include *
            include user
            autoLayout lr
        }

        styles {
            element "Person" {
                shape person
                background #08427b
                color #ffffff
            }

            element "Software System" {
                background #1168bd
                color #ffffff
            }

            element "External" {
                background #999999
                color #ffffff
            }

            element "Container" {
                background #438dd5
                color #ffffff
            }

            element "Component" {
                background #85bb65
                color #ffffff
            }
        }
    }
}