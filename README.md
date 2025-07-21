# AIRFLOWX

## Overview

Airflowx (afx) is a command line application to interact with the airflow deployment via its REST
API.

Find the summary of supported commands below. Detailed documentation of the commands can be
found [here](COMMANDS.md).

```
afx config set-context [-h] <context-name> [--server=<URL>] [--username=<name>] [--password=<pass>]
afx config get-contexts [-h]
afx config current-context [-h]
afx config use-context [-h] <context-name>
afx config remove-context [-h] <context-name>
afx dags list [-h] [--only-active] [--paused] [--pattern-string=<dagIdPatternString>]
afx dags list-runs [-h] <DAG ID> [--sort-reverse] [--limit=<number>] [--state=<states>]
afx dags stat [-h] [--all] [<dagIdList>[,<dagIdList>...]]
afx dags details [-h] <DAG ID> [-v]
afx dags pause [-h] [--all] [--dag-id=<dagId>] [--pattern=<dagIdPattern>]
afx dags unpause [-h] [--all] [--dag-id=<dagId>] [--pattern=<dagIdPattern>]
afx modify run [-h] <DAG ID> --run=<DAG Run ID> --state=<state>
afx import-errors [-h]
```

## Running the app

Grab the uber jar file or the native linux binary from the latest release.

### Running the uber jar

The jar needs Java `21.0.6` or newer to run. Run the jar using:

```shell
java -jar airflowx-*.jar <argument-1> <argument-2>
```

For easier syntax set up an alias to run the jar.

### Running the linux native binary

This binary is built and tested on linux. Start by renaming the native binary file to `afx`.
Add the file path to `PATH` or move the `afx` binary to a location that already exists in the `PATH`
variable.

To have auto-completion run the [afx-completion](afx-completion) bash script in the bash shell.

## Limitations

- At the moment this CLI app supports the `api/v1` airflow endpoint. This CLI app was tested with
  airflow `2.10.5`.
- The CLI app currently supports the `basic_auth` airflow backend authentication only.
- No test coverage, oops!!

---

## Development

This project uses [Quarkus](https://picocli.info/), the Supersonic Subatomic Java Framework
and [picocli](https://picocli.info/).

This needs Java 21.0.6 or newer. To build the native executable it
needs [GraalVM](https://www.graalvm.org/).

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only
> at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```
./gradlew build
```

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the
`build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```
./gradlew build -Dquarkus.native.enabled=true
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container
using:

```
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/*-runner`

If you want to learn more about building native executables, please
consult <https://quarkus.io/guides/gradle-tooling>.

## Auto completion

To have command auto-completion in bash install the [afx-completion](afx-completion) bash script.
Airflowx can auto complete the available dag ids in relevant commands on pressing tab twice.

Picocli can automatically recreate the bash auto-completion script. Before creating the
auto-completion script build the jar using `./gradlew clean build`.

On windows recreate the afx-completion script using:

```shell
java -cp "build/quarkus-app/app/*;build/quarkus-app/lib/main/*;build/quarkus-app/quarkus-run.jar" \ 
picocli.AutoComplete -n afx \ 
--force com.airflowx.command.AirflowxCommand \ 
--completionScript afx-completion
``` 

On linux recreate the afx-completion script using (note the semicolon to colon change):

```shell
java -cp "build/quarkus-app/app/*:build/quarkus-app/lib/main/*:build/quarkus-app/quarkus-run.jar" \ 
picocli.AutoComplete -n afx \ 
--force com.airflowx.command.AirflowxCommand \ 
--completionScript afx-completion
```
