plugins {
    id("java-library")
}

description = "Java Ripper Core Module"

dependencies {
    api("org.slf4j:slf4j-api")

    runtimeOnly("ch.qos.logback:logback-classic")
}
