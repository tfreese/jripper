plugins {
    id("java-library")
}

description = "Java Ripper Core Module"

dependencies {
    api("org.apache.commons:commons-lang3")
    api("org.slf4j:slf4j-api")

    runtimeOnly("ch.qos.logback:logback-classic")
}

// test {
//     enabled = false
//     exclude "de/freese/jripper/core/**"
//     exclude "de/freese/jripper/core/TestDiskID.class"
//     exclude "de/freese/jripper/core/TestRipper.class"
// }
