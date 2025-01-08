# CICD Process

A new build job can be triggered either manually or when a new git commit is pushed.

When job finishes, the build artifacts are uploaded to Github and become Job Artifacts.

<br>

When build job is triggered on `main` branch, a new Git tag will be created. Additional `Create Github Release` job will be executed to create a Github Release.

When new Github release is created, jar file will also become release artifact.

<br>
