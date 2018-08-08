# VrrDepartureMonitor

Tiny train monitoring JavaFX-Application for Desktops

## Building

The project uses gradle to build. A gradlewrapper is added to the project. All you have to do from cli is:

    ./gradlew build
    
or

    gradlew build
    
on windows. In the rest of the documentation the unix-style (read: the better style) is used.

The build process generates a zip-file at `build/distributions/VrrDepartureMonitore-X.X.X.zip` which contains a `bin/` directory with a unix and a windows executable and a `lib/` directory with all dependencies. That bundle can be deployed to the target device.

### Run tests

Run

    ./gradlew test

## Importing to IntelliJ

Open the build.gradle file from this repository with IntelliJ and choose to use the included gradlewrapper for building. IntelliJ will have a special tool window for it which you can access at `View -> Tool Windows -> Gradle`. It has a sync-button at the top left. Whenever you change the build.gradle file, the project must be synced. There is an option to do that automatically in recent versions of IntelliJ.
