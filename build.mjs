import { connect } from "@dagger.io/dagger"

// initialize Dagger client
connect(
    async (client) => {
        // get maven image
        const maven = client.container().from("maven:3-eclipse-temurin-21")

        // create a cache volume for Maven downloads
        const mavenCache = client.cacheVolume("maven-cache")

        const source = client.host().directory(".")

        const MAVEN_CLI_OPTS = `--batch-mode
    --errors
    --fail-at-end
    --show-version
    --no-transfer-progress
    -DinstallAtEnd=true
    -DdeployAtEnd=true`

        const runner = maven
            .withDirectory("/src", source)
            .withWorkdir("/src")
            .withMountedCache("/root/.m2", mavenCache)
            .withEnvVariable("MAVEN_CLI_OPTS", MAVEN_CLI_OPTS)
            .withExec(["mvn", "verify"])


        const SONAR_TOKEN = client.setSecret("SONAR_TOKEN", process.env.SONAR_TOKEN)

        // execute
        await runner
            .withSecretVariable("SONAR_TOKEN", SONAR_TOKEN)
            .withExec(["mvn",
            "org.sonarsource.scanner.maven:sonar-maven-plugin:sonar",
            "-Dsonar.projectKey=gitlab-classrooms_gitlab-classrooms",
            "-Dsonar.organization=gitlab-classrooms",
            "-Dsonar.host.url=https://sonarcloud.io"]).sync()


    },
    { LogOutput: process.stderr }
)
