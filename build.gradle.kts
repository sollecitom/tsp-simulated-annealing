import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.0"
}

repositories {
    mavenCentral()
}

buildscript {

    extra["junit_version"] = "5.3.1"
    extra["assertj_version"] = "3.11.1"
}

allprojects {
    group = "sollecitom.exercises.tsp"
    version = "1.0-SNAPSHOT"

    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        compile(kotlin("stdlib-jdk8"))
        compile(kotlin("reflect"))
        testCompile(kotlin("test"))
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<JavaCompile> {
        enabled = false
    }
}

val thisProject = this
subprojects {
    thisProject.extra.properties.forEach { key, value ->
        extra[key] = value
    }

    dependencies {
        "org.junit.jupiter".let {

            val junit_version: String by extra

            compile(group = it, name = "junit-jupiter-api", version = junit_version)
            compile(group = it, name = "junit-jupiter-engine", version = junit_version)
        }
        "org.assertj".let {

            val assertj_version: String by extra

            compile(group = it, name = "assertj-core", version = assertj_version)
        }
    }
}