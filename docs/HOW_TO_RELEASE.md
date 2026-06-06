# How to release a new version

Using plugin https://github.com/DanySK/publish-on-central

Needs to be logged in to Sonatype OSSRH (credentials in ~/.gradle/gradle.properties)

Signing plugins needs to have GPG keys working (see https://docs.gradle.org/current/userguide/signing_plugin.html)

```bash
./gradlew clean

./gradlew publishAllPublicationsToProjectLocalRepository

./gradlew releaseMavenCentralPortalPublication
```

