# ESI Project Backend - Team J

[![Deploy](https://github.com/chaosrun/ESI-project-backend/actions/workflows/heroku-deploy.yml/badge.svg)](https://github.com/chaosrun/ESI-project-backend/actions/workflows/heroku-deploy.yml) [![Heroku](https://pyheroku-badge.herokuapp.com/?app=esi-project-team-j&style=flat)](https://esi-project-team-j.herokuapp.com) 

## PostgresSQL settings

In order to keep the GitHub Actions jobs passing, please make sure that the following variables are set in `application-default.properties`:

```
spring.datasource.url = jdbc:postgresql://localhost:5432/esidb
spring.datasource.username = esi
spring.datasource.password = esi
```

You can create the database with the following command:

```bash
sudo -u postgres psql
```

```sql
postgres=# create database esidb;
postgres=# create user esi with encrypted password 'esi';
postgres=# grant all privileges on database esidb to esi;
```

## Git workflow example

Work on your feature branch:

```bash
git checkout -b feature_branch_name
```

Please make sure that your feature branch is up to date with the latest changes in main before you create a pull request. You can choose to rebase or merge. If you have a public feature branch (already pushed and will collaborate with others on this feature branch), you may use merge instead, but here we take rebase as an example:

```bash
git checkout main
git pull
git checkout feature_branch_name
git rebase main
git push
```

The rebase will usually go through without issues, but if git can not merge the changes automatically, a merge conflict will arise. If this happens, you can edit the files manually to resolve the conflicts. Then add files to the index, and continue the rebase:

```bash
git add path/to/file
git rebase --continue
```

You may need to use force update if you already pushed this feature branch to GitHub:

```bash
git push --force-with-lease
```

Further reading: [Resolving merge conflicts](https://docs.openstack.org/doc-contrib-guide/additional-git-workflow/rebase.html)

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

Test:

```bash
curl "https://esi-project-team-j.herokuapp.com/auth" -u "borrower@example.com:QdSBhhujeH7ki2#X"
```

## Routes

| URI   | HTTP Verb | Description                                               |
|-------|-----------|-----------------------------------------------------------|
| /auth | GET       | Return the authenticated user details, including the role |

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

## Git

- [Merge Master into Branch before making new PR](https://www.reddit.com/r/git/comments/je457m/merge_master_into_branch_before_making_new_pr/)
- [VSTS: how to require a branch to be up to date before merging (doing pull request) from that branch?](https://stackoverflow.com/questions/64029333/vsts-how-to-require-a-branch-to-be-up-to-date-before-merging-doing-pull-reques)
- [Recommended git workflow](https://docs.gpcrdb.org/git_workflow.html)
- [About protected branches](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/defining-the-mergeability-of-pull-requests/about-protected-branches)
- [Git Branching - Rebasing](https://git-scm.com/book/en/v2/Git-Branching-Rebasing)
- [Merging vs. Rebasing](https://www.atlassian.com/git/tutorials/merging-vs-rebasing)
