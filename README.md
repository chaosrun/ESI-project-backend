# ESI Project Backend - Team J

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

## Users

- Borrower
    - Email: `borrower@example.com`
    - Password: `QdSBhhujeH7ki2#X`
    - Basic authentication base64 token: `Ym9ycm93ZXJAZXhhbXBsZS5jb206UWRTQmhodWplSDdraTIjWA==`
- Librarian
    - Email: `librarian@example.com`
    - Password: `t4CvQm!R4SvRmzy$`
    - Basic authentication base64 token: `bGlicmFyaWFuQGV4YW1wbGUuY29tOnQ0Q3ZRbSFSNFN2Um16eSQ=`
- Authorization: Basic Auth

## Routes

| URI        | HTTP Verb | Description                          |
|------------|-----------|--------------------------------------|
| /user/auth | GET       | Return roles list                    |
| /user/me   | GET       | Return user's email address and name |

## References

### CI/CD

- [Creating user, database and adding access on PostgreSQL](https://medium.com/coding-blocks/creating-user-database-and-adding-access-on-postgresql-8bfcd2f4a91e)
- [Deploying Spring Boot Applications to Heroku](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku)
- [Deploying Spring Boot application to Heroku with PostgreSql](https://levelup.gitconnected.com/deploying-spring-boot-application-to-heroku-with-postgresql-dc94f193464c)
- [Create Java Spring API with VSCODE & Postgres: 0 to Deploy ](https://dev.to/alexmercedcoder/create-java-spring-api-with-vscode-postgres-0-to-deploy-142)
- [Deploy to Heroku with GitHub Actions](https://remarkablemark.org/blog/2021/03/12/github-actions-deploy-to-heroku/)
- [The simplest way to include a Heroku badge in your README file](https://github.com/dhalenok/pyheroku-badge)
- [Stop GitHub Jobs in Progress if Another Failed (stop on fail)](https://stackoverflow.com/questions/67488957/stop-github-jobs-in-progress-if-another-failed-stop-on-fail)
- [Creating PostgreSQL service containers](https://docs.github.com/en/actions/using-containerized-services/creating-postgresql-service-containers)

### Database

- [How to fix "Error executing DDL "alter table events drop foreign key FKg0mkvgsqn8584qoql6a2rxheq" via JDBC Statement"](https://stackoverflow.com/a/56206827)
    - Changed `@Table(name = "user")` to `@Table(name = "users")` in `users/User.java`.
- [TRUNCATE](https://www.postgresql.org/docs/current/sql-truncate.html)

### Security

- [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
- [Multiple markers at this line - The type NoOpPasswordEncoder is deprecated - The method getInstance() from the type NoOpPasswordEncoder is deprecated](https://stackoverflow.com/questions/52134823/multiple-markers-at-this-line-the-type-nooppasswordencoder-is-deprecated-the)
- [4 Most Used REST API Authentication Methods](https://blog.restcase.com/4-most-used-rest-api-authentication-methods/)
- [Basic Authentication](https://mixedanalytics.com/knowledge-base/api-connector-encode-credentials-to-base-64/)
- [Java Code Examples for BCryptPasswordEncoder](https://www.tabnine.com/code/java/classes/org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder)
- [How to find out the currently logged-in user in Spring Boot?](https://stackoverflow.com/a/51944119)
