// Execute Tasks in SubModule: gradle MODULE:clean build
plugins {
    id("de.freese.gradle.conventions").apply(false)
    id("io.spring.dependency-management").apply(false)
    id("org.springframework.boot").apply(false)
}

allprojects {
    plugins.apply("base")
}

subprojects {
    plugins.apply("de.freese.gradle.conventions")
    plugins.apply("io.spring.dependency-management")

    // Workaround for Exception: SourceSet with name 'test' not found.
    // if (file("src/main/java").isDirectory()) {
    //     apply(plugin: "name.remal.sonarlint")
    // }

    extensions.configure(io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension::class.java) {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:" + property("version_springBoot"))
        }

        dependencies {
            // dependency("GROUP:ARTIFACT:" + property("version_XXX"))
            dependencySet("com.formdev:" + property("version_flatLaf")) {
                entry("flatlaf")
                entry("flatlaf-intellij-themes")
            }
        }
    }

    pluginManager.withPlugin("java") {
        dependencies {
            add("testImplementation", "org.junit.jupiter:junit-jupiter")
            add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher")
        }
    }
}
