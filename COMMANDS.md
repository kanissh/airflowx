# Airflowx CLI Help Documentation

## `afx`

**Description:** CLI application to interact with Airflow via the stable REST API.

```
afx [command] [options]
```  

### Commands:

1. `config` - Manage CLI configurations
2. `dags` - Manage DAGs
3. `modify` - Modify DAG runs in the Airflow server
4. `import-errors` - Manage import errors

Run `afx [command] --help` for more details.

---

## 1. `afx config`

**Description:** Manage CLI configurations.

```
afx config [subcommand] [options]
```  

### Subcommands:

1. `set-context` - Configure the specified context with the given arguments.
2. `get-contexts` - Display all configured contexts.
3. `current-context` - Show the current context.
4. `use-context` - Switch to a different configured context.
5. `remove-context` - Remove a specific context.

---

### 1.1 `afx config set-context`

**Description:** Configure a context with specific parameters.

```
afx config set-context [-h] <context-name> [--server=<URL>] [--username=<name>] [--password=<pass>]
```  

**Options:**

- `-h, --help` - Show help message and exit.
- `<context-name>` - The name of the context (required).
- `--server=<URL>` - URL of the Airflow instance (required).
- `--username=<name>` - Username for basic authentication (required).
- `--password=<pass>` - Password for basic authentication (required).

---

### 1.2 `afx config get-contexts`

**Description:** Display all configured contexts.

```
afx config get-contexts [-h]
```  

**Options:**

- `-h, --help` - Show help message and exit.

---

### 1.3 `afx config current-context`

**Description:** Show the current context.

```
afx config current-context [-h]
```  

**Options:**

- `-h, --help` - Show help message and exit.

---

### 1.4 `afx config use-context`

**Description:** Switch to a different configured context.

```
afx config use-context [-h] <context-name>
```  

**Options:**

- `-h, --help` - Show help message and exit.
- `<context-name>` - The name of the context to switch to (required).

---

### 1.5 `afx config remove-context`

**Description:** Remove a specific context.

```
afx config remove-context [-h] <context-name>
```  

**Options:**

- `-h, --help` - Show help message and exit.
- `<context-name>` - The name of the context to remove (required).

---

## 2. `afx dags`

**Description:** Manage DAGs.

```
afx dags [subcommand] [options]
```  

### Subcommands:

1. `list` - List DAGs available in the server (Alias: ls)
2. `list-runs` - List DAG runs given a DAG ID (Alias: ls-runs)
3. `stat` - Get statistics of DAGs
4. `details` - Get DAG details given the DAG ID
5. `pause` - Pause DAG/DAGs, order of priority is --all, --pattern, --dag-id
6. `unpause` - Unpause DAG/DAGs, order of priority is --all, --pattern, --dag-id

---

### 2.1 `afx dags list`

**Description:** List DAGs available in the server.

```
afx dags list [-h] [--only-active] [--paused] [--pattern=<dagIdPattern>]
```  

**Options:**

- `-h, --help` - Show help message and exit.
- `--only-active` - Retrieve only active DAGs.
- `--pattern=<dagIdPattern>` - Pattern string to match with DAG ID.
- `--paused` - Retrieve paused DAGs (`true`), unpaused DAGs (`false`), or all if not set.

---

### 2.2 `afx dags list-runs`

**Description:** List DAG runs for a specific DAG ID.

```
afx dags list-runs [-h] <DAG ID> [--sort-reverse] [--limit=<number>] [--state=<states>]
```  

**Options:**

- `-h, --help` - Show help message and exit.
- `<DAG ID>` - The ID of the DAG (required).
- `--sort-reverse` - Order by ascending start date.
- `--limit=<number>` - Limit the number of results (default: 100).
- `--state=<states>` - Filter runs by state (e.g., `queued`, `running`, `success`).

---

### 2.3 `afx dags stat`

**Description:** Get statistics of DAGs.

```
afx dags stat [-h] [--all] [<dagIdList>[,<dagIdList>...]]
```  

**Options:**

- `-h, --help` - Show help message and exit.
- `--all` - Include statistics for all DAGs.
- `<dagIdList>` - Comma separated list of DAG ids.

---

### 2.4 `afx dags details`

**Description:** Display detailed information about a specific DAG.

```
afx dags details [-h] <DAG ID> [-v]
```  

**Options:**

- `-h, --help` - Show help message and exit.
- `<DAG ID>` - The ID of the DAG (required).
- `-v, --verbose` - Display detailed information.

---

### 2.5 `afx dags pause`

**Description:** Pause DAG/DAGs

```
afx dags pause [-h] [--all] [--dag-id=<dagId>] [--pattern=<dagIdPattern>] 
```  

**Options:**

- `-h, --help` - Show help message and exit.
- `--all` - Pause all DAGs
- `<dagId>` - The ID of the DAG.
- `<dagIdPattern>` - Pause matching DAG id pattern

---

### 2.5 `afx dags unpause`

**Description:** Pause DAG/DAGs

```
afx dags unpause [-h] [--all] [--dag-id=<dagId>] [--pattern=<dagIdPattern>] 
```  

**Options:**

- `-h, --help` - Show help message and exit.
- `--all` - Pause all DAGs
- `<dagId>` - The ID of the DAG.
- `<dagIdPattern>` - Pause matching DAG id pattern

---

## 3. `afx modify`

**Description:** Modify DAG runs on the Airflow server.

```
afx modify [subcommand] [options]
```  

### Subcommands:

1. `run` - Modify the state of a specific DAG run.

---

### 3.1 `afx modify run`

**Description:** Modify the state of a specific DAG run.

```
afx modify run [-h] <DAG ID> --run=<DAG Run ID> --state=<state>
```  

**Options:**

- `-h, --help` - Show help message and exit.
- `<DAG ID>` - The ID of the DAG (required).
- `--run=<DAG Run ID>` - The run ID of the DAG (required).
- `--state=<state>` - The state to set for the DAG run (required).

---

## 4. `afx import-errors`

**Description:** Manage import errors.

```
afx import-errors [-h]
```  

**Options:**

- `-h, --help` - Show help message and exit.