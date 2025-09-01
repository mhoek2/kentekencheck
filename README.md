# Kenteken Check

This application is developed using Java and Swing.
It uses fetches dutch vehicle information using the RDW opendata API.
Then displays the amount of days until the APK (Periodic Technical Inspection - PTI) is due, including additional vehicle information.

## Includes:
- [x] Github ci-cd to create [documentation](https://mhoek2.github.io/kentekencheck/) as github static pages using [javadoc](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html).
- [x] Github ci-cd to create windows (.exe) and linux (.deb) installer binaries using [jpackage](https://docs.oracle.com/en/java/javase/17/docs/specs/man/jpackage.html)
- [x] Core principle of extendable API Class

The primary goal of this project is to gain more knowledge in Java programming.

## Installation:
1. go to the latest or beta releases [here](https://github.com/mhoek2/kentekencheck/releases)
2. Download the installer required for your operating system.
3. Launch the installer and follow the setup guide.

## Preview:
![preview](https://github.com/user-attachments/assets/368622c5-5af3-44f9-89d4-1f695cfffd8c)

## Development
#### Prerequisite
1. Download gson-2.10.1.jar and place in vendor folder

#### Run jpackage locally:
1. Open Windows powershell and run:
```
  cd into applicationroot
  javac -d mods/kentekencheck --module-path "vendor\gson-2.10.1.jar" --add-modules com.google.gson --add-reads kentekencheck=ALL-UNNAMED (Get-ChildItem -Recurse -Filter *.java).FullName
  jar --create --file=build/output.jar --module-version=1.0 -C mods/kentekencheck .
```
2. Open Windows command prompt and run:
```
  cd into applicationroot/build
  jpackage @pack.jpack
```

#### Run javadoc locally
1. Open Windows command prompt and run:
```
  cd into applicationroot
  javadoc -d docs -classpath "vendor\gson-2.10.1.jar" src/kentekencheck/*.java
```

#### Sources
- [RDW API information](https://opendata.rdw.nl)
