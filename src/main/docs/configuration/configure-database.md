# database configuration

The following properties are used to configure database access.

Gitlab-Classroom initializes the database when it starts, so there's no need to create anything upfront.

Gitlab-Classroom only supports PostgreSQL 14. 

## Using env vars

| env var                     | usage               | default value |
|-----------------------------|---------------------|---------------|
| `POSTGRESQL_ADDON_HOST`     | PostgreSQL Hostname |               |
| `POSTGRESQL_ADDON_PORT`     | PostgreSQL Port     |               |
| `POSTGRESQL_ADDON_DB`       | PostgreSQL Database |               |
| `POSTGRESQL_ADDON_USER`     | PostgreSQL User     |               |
| `POSTGRESQL_ADDON_PASSWORD` | PostgreSQL Password |               |
