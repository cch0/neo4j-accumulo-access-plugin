# CICD Process

A new build job can be triggered either manually or when a new git commit is pushed.

When job finishes, the build artifacts are uploaded to Github and become Job Artifacts.

<br>

When build job is triggered on `main` branch, a new Git tag will be created. Additional `Create Github Release` job will be executed to create a Github Release.

<br>

<b>Artifact in Maven repository</b>

Plugin jar file is also published to Github Maven repository and is accessible via the following maven dependency.

```xml
<dependency>
  <groupId>com.neo4j.data.edl</groupId>
  <artifactId>neo4j-accumulo-access</artifactId>
</dependency>
```

<br>
