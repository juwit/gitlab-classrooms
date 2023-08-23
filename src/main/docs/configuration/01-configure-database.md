# Database configuration

The following properties are used to configure database access.

_GitLab Classroom_ initializes the database schema when it starts, so there's no need to create anything upfront.

_GitLab Classroom_ only supports PostgreSQL 14. 

## Configure database connectivity using env vars

| env var                     | usage               | default value |
|-----------------------------|---------------------|---------------|
| `POSTGRESQL_ADDON_HOST`     | PostgreSQL Hostname |               |
| `POSTGRESQL_ADDON_PORT`     | PostgreSQL Port     |               |
| `POSTGRESQL_ADDON_DB`       | PostgreSQL Database |               |
| `POSTGRESQL_ADDON_USER`     | PostgreSQL User     |               |
| `POSTGRESQL_ADDON_PASSWORD` | PostgreSQL Password |               |
