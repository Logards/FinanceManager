plugins {
    java
    application
    id("org.javamodularity.moduleplugin") version "1.8.12"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "3.0.1"
}

group = "com.example"
version = "1.0"

repositories {
    mavenCentral()
}

val junitVersion = "5.10.2"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("com.example.financemanager")
    mainClass.set("com.example.financemanager.Application")
}

javafx {
    version = "17.0.6"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.44.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "FinanceManager"
    }
    jpackage {
        if (org.gradle.internal.os.OperatingSystem.current().isLinux) {
            // Configuration Linux
            imageOptions.addAll(listOf("--icon", "src/main/resources/icons/icon.png"))
            skipInstaller = false
            installerType = "deb"
            installerOptions = listOf(
                "--linux-package-name", "finance-manager",
                "--linux-deb-maintainer", "example@example.com"
            )
        } else if (org.gradle.internal.os.OperatingSystem.current().isWindows) {
            imageOptions.addAll(listOf("--icon", "src/main/resources/icons/icon.ico"))
            skipInstaller = false
            installerType = "msi"
            installerOptions = listOf(
                "--win-dir-chooser",
                "--win-menu",
                "--win-shortcut",
                "--win-per-user-install"
            )
        } else if (org.gradle.internal.os.OperatingSystem.current().isMacOsX) {
            imageOptions.addAll(listOf("--icon", "src/main/resources/icons/icon.icns"))
            skipInstaller = false
            installerType = "pkg"
            installerOptions = listOf(
                "--mac-package-name", "FinanceManager",
                "--mac-package-identifier", "com.example.financemanager"
            )
        }
    }
}
