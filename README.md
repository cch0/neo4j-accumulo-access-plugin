# Neo4j Accumulo Access Plugin

This repository contains the implementation of a Neo4j plugin for applying Accumulo's attribute-based-access-control (ABAC) to nodes in Neo4j.



<br>

## How To Build

<b>Prerequisites</b>

- JDK: 17


<br>

<b>Build</b>

```bash
mvn clean package
```

<br>

<b>Build Artifact</b>

Build artifact is located under `./target` directory.

```bash
>ls -l target

-rw-r--r--  1 cch  staff  3899710 Jan  6 16:20 accumulo-access-0.10.0-SNAPSHOT.jar
```

<br>

## Release Artifacts


When a new Github release is created, plugin jar file will be included as part of the release assets.
Go to Release [page](https://github.com/cch0/neo4j-accumulo-access-plugin/releases) and download the appropriate jar file.

<br>

## Installation

Copy the jar file to the Neo4j server, for example, `/plugins/accumulo-access.jar` and start the service.


<br>

## CICD Process

See [cicd](./docs/cicd.md) for details.

<br>

## Usage

See [usage](./docs/usage.md) for details.

<br>


