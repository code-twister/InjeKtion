plugins {
    kotlin("jvm") version "2.2.10"
    id ("org.danilopianini.publish-on-central") version "9.1.0"
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

publishOnCentral {
    repoOwner.set("code-twister") // Used to populate the default value for projectUrl and scmConnection
    projectDescription.set("A minimalistic dependency injection library for Kotlin.")
    // The following values are the default, if they are ok with you, just omit them
    projectLongName.set("injeKtion")
    licenseName.set("Gnu General Public License v3.0")
    licenseUrl.set("https://www.gnu.org/licenses/gpl-3.0.en.html")
    projectUrl.set("https://github.com/${repoOwner.get()}/InjeKtion")
    scmConnection.set("scm:git:https://github.com/${repoOwner.get()}/InjeKtion")
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                developers {
                    developer {
                        name = "Nagy Mihaly Ferencz"
                        email = "mihaly.f.nagy@gmail.com"
                        organization = "codetwister"
                        organizationUrl = "https://codetwister.dev"
                    }
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
}
