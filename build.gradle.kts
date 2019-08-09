import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    val kotlinVersion = "1.3.41"
    val springDependencyManagementVersion = "1.0.7.RELEASE"
    val bintrayVersion = "1.8.4"

    kotlin("jvm") version kotlinVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion
    id("java-library")
    id("com.jfrog.bintray") version bintrayVersion
    `maven-publish`
}

group = "it.github.enricosecondulfo"
version = findProperty("version") as Any

repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone")
    maven("https://repo.spring.io/snapshot")
    maven("https://dl.bintray.com/hotkeytlt/maven")
    jcenter()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("testScript") {
    doLast {
        val username: String by project
        val password: String by project

        println("test " + username + " + test " + password)
    }
}

/* tasks.register("findVersion") {
    val properties = Properties()
    val inputStream = project.file("gradle.properties").inputStream()
    properties.load(inputStream)
    println(properties.getProperty("version"))
    properties.setProperty("version", "2.0.3")
    properties.store(project.file("gradle.properties").outputStream(), "")
    println(properties.getProperty("version"))
} */


val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

fun findProperty(property: String) = project.findProperty(property) as String?

bintray {
    user = findProperty("bintrayUser")
    key = findProperty("bintrayApiKey")
    publish = true
    setPublications("mavenJava")
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = findProperty("artifactId")
        websiteUrl = "https://github.com/enricosecondulfo/travis-ci-test"
        githubRepo = "enricosecondulfo/travis-ci-test"
        vcsUrl = "https://github.com/enricosecondulfo/travis-ci-test"
        description = "test description"
        setLabels("kotlin")
        setLicenses("Apache-2.0")
        version(delegateClosureOf<BintrayExtension.VersionConfig> {
            name = findProperty("version")
            vcsTag = findProperty("version")
        })
    })
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            groupId = findProperty("groupId")
            artifactId = findProperty("artifactId")
            version = findProperty("version")
            artifact(sourcesJar.get())
        }
    }
}

dependencyManagement {
    val springBootVersion: String by project

    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.springframework.data:spring-data-mongodb")
    implementation("org.mongodb:mongodb-driver-reactivestreams")
    implementation("com.github.h0tk3y.betterParse:better-parse-jvm:0.4.0-alpha-3")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "junit", module = "junit")

    }
}