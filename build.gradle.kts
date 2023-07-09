import com.google.protobuf.gradle.*
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    application
    id("com.google.protobuf") version "0.9.3"
    id("java")
    idea
}

apply(plugin = "java")
java.sourceCompatibility = JavaVersion.toVersion("17")
java.targetCompatibility = JavaVersion.toVersion("17")

repositories {
    google()
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(platform("io.grpc:grpc-bom:1.56.1"))
    implementation("com.google.protobuf:protobuf-java:3.22.0")
    implementation("io.grpc:grpc-api")
    implementation("io.grpc:grpc-stub")
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-services")
    implementation("io.grpc:grpc-core")
    implementation("org.jdbi:jdbi3-sqlobject:3.39.1")
    implementation("org.jdbi:jdbi3-core:3.39.1") {
        exclude ("com.github.ben-manes.caffeine:caffeine")
    }
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.jdbi:jdbi3-postgres:3.39.1")
    implementation("org.postgresql:postgresql:42.5.0")

    compileOnly("org.apache.tomcat:annotations-api:6.0.53")
    implementation("io.grpc:grpc-netty-shaded")

    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.testcontainers:postgresql:1.18.3")
}

application {
    // Define the main class for the application.
    mainClass.set("grpc.jdbi.example.App")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.22.3"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.56.1"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs("$buildDir/generated/source/proto/main/grpc")
            srcDirs("$buildDir/generated/source/proto/main/java")
        }
    }
}

tasks.withType<Test> {
    testLogging {
        // set options for log level LIFECYCLE
        events(
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED,
                TestLogEvent.STANDARD_OUT
        )
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true

        // set options for log level DEBUG and INFO
        debug {
            events(
                    TestLogEvent.STARTED,
                    TestLogEvent.FAILED,
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED,
                    TestLogEvent.STANDARD_ERROR,
                    TestLogEvent.STANDARD_OUT
            )
            exceptionFormat = TestExceptionFormat.FULL
        }
        info.events = debug.events
        info.exceptionFormat = debug.exceptionFormat
    }
}

