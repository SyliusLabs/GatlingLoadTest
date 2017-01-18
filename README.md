# Sylius Load Test

## Installation

1. Download [Gatling](http://gatling.io/#/resources/download) (ZIP archive).
2. Put contents of this repository in `user-files`.


## Usage

`bin/gatling.sh -s sylius.SyliusLoadTest`

You can configure the way it works by passing Java options to the process:

### Configuration options

| Option                | Description
|-----------------------|------------
| `load.numberOfUsers`  | Number of concurrent users on site
| `load.rampingTime`    | Time it takes for users to visit the site (in seconds)
| `load.simulationTime` | Time the application is tested (in minutes)
| `project.name`        | Project name
| `project.domain`      | Project domain

`JAVA_OPTS="-Dproject.domain=example.com" bin/gatling.sh -s sylius.SyliusLoadTest`
