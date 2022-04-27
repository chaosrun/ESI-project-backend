# ESI-project-backend

[![Heroku](https://pyheroku-badge.herokuapp.com/?app=esi-project-team-j&style=flat)](https://esi-project-team-j.herokuapp.com)

## PostgresSQL settings

```bash
sudo -u postgres psql
```

```sql
postgres=# create database esidb;
postgres=# create user esi with encrypted password 'esi';
postgres=# grant all privileges on database esidb to esi;
```

## References

[Creating user, database and adding access on PostgreSQL](https://medium.com/coding-blocks/creating-user-database-and-adding-access-on-postgresql-8bfcd2f4a91e)

[Deploying Spring Boot Applications to Heroku](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku)

[Deploying Spring Boot application to Heroku with PostgreSql](https://levelup.gitconnected.com/deploying-spring-boot-application-to-heroku-with-postgresql-dc94f193464c)

[Create Java Spring API with VSCODE & Postgres: 0 to Deploy ](https://dev.to/alexmercedcoder/create-java-spring-api-with-vscode-postgres-0-to-deploy-142)

[Deploy to Heroku with GitHub Actions](https://remarkablemark.org/blog/2021/03/12/github-actions-deploy-to-heroku/)
