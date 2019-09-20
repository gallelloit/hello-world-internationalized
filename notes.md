# RESTFul Web Services

Social Media Application

User -> Posts (1..n)

- Retrieve all users    - GET /users
- Create a user         - PUT /users
- Retrieve one user     - GET /users/{id} -> /users/1
- Delete one user       - DELETE /users/{id} -> /users/1

- Retrieve all posts for a user     - GET /users/{id}/posts
- Create a post for a user          - PUT /users/{id}/posts
- Retrieve details for a post       - GET /users/{id}/posts/{post_id}