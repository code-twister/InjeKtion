plugins {
    kotlin("jvm") version "2.2.10"
    id("maven-publish")
    signing
}

group = "dev.codetwister"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            pom {
                name = "injeKtion"
                description = "A minimalistic dependency injection library for Kotlin."
                url = "https://github.com/code-twister/InjeKtion"
                licenses {
                    license {
                        name = "GNU General Public License v3.0"
                        url = "https://www.gnu.org/licenses/gpl-3.0.en.html"
                    }
                }
                developers {
                    developer {
                        name = "Nagy Mihaly Ferencz"
                        email = "mihaly.f.nagy@gmail.com"
                        organization = "codetwister"
                        organizationUrl = "https://codetwister.dev"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/code-twister/InjeKtion.git"
                    developerConnection = "scm:git:ssh://github.com:code-twister/InjeKtion.git"
                    url = "https://github.com/code-twister/InjeKtion"
                }
            }
        }
    }
}

signing {
    val signingKeyId = project.findProperty("signingKeyId").toString()
    val signingKey = project.findProperty("signingKey").toString()
    val signingPassword = project.findProperty("signingPassword").toString()
    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
    sign(publishing.publications["maven"])
}
