# Trinat Desktop App V2022 (Java Fx)

Repository: 
[https://github.com/mbachmann/trinat-desktop-2022](https://github.com/mbachmann/trinat-desktop-2022)

### Installers

Installers for _macOs_ or _Windows_ can be found in the folder installers.
The installer provides a _JPMS-based_ Java runtime environment based on _JDK17_.
This means, the _Trinat App_ should run without a separate _JDK17_ installation.

The installation has been tested with macOS Big Sur (Intel),
macOs Monterey (Apple Silicon) and Windows 10.

### MacOs Security issue

At the start of the _Trinat App_ you will get a message:

**Diese Anwendung ist beschädigt und kann nicht geöffnet werden**. 

The reason for this message is the absence of a valid apple developer id signing. 
To solve the problem do the following after the installation. 

1. Open a terminal
2. Navigate to Application
3. Enable the start of the application

```sh
cd /Applications
sudo xattr -r -d com.apple.quarantine trinatapp.app
```

The Trinat App should start.

**macOs Installer**


![assets/macOs.png](assets/macOs.png)

<br/>

**Windows Installer**

![assets/win-installer.png](assets/win-installer.png)

<br/>

### Required Tools for development

- JDK17 or OpenJDK17
- Maven (the project contains the maven wrapper: Windows mvnw, macOs oder Linux ./mvnw)

### Run the project from the IDE

Open the folder _src/main/java_ and package _com.example_ and start the Launcher

### Run the project from the command line

```sh
mvn clean compile javafx:run
```
For this modular projects, create and run a custom image:

```sh
mvn clean compile javafx:jlink

target/trinatapp/bin/java -m com.example/com.example.Launcher
```

### Build an installer

Cross compilation is not possible. Mac installer only on Mac, Windows installer only on windows.
The new installer can be found in the _target/dist_ folder.

For macOS:

```shell
mvn clean compile javafx:jlink -P macOs jpackage:jpackage
```

For easy installation use Apple Developer license and sign the installer. 
Without signing the message **Diese Anwendung ist beschädigt und kann nicht geöffnet werden**. will appear.

For Windows:

```shell
mvn clean compile javafx:jlink -P win jpackage:jpackage
```
