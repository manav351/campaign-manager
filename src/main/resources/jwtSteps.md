#### Link: https://github.com/CodeMythGit/ReadMeNotes/blob/main/SpringJwtImplementation_Notes.md

### Checklist to implement JWT Authentication:
-[x] Create Basic Rest API.
-[x] Secure the Rest API by adding spring security dependency.
-[ ] Use the properties file to create custom username and password for authentication (NOT REQUIRED)
-[x] Create the SpringSecurityConfig class to define the bean like PasswordEncoder, UserDetailsService and AuthenticationManager
-[x] Create JwtAuthenticationEntryPoint Class Which implements AuthenticationEntryPoint interface and override method commence
-[x] Create JwtHelper Class which is used to perform action like validateToken and generateToken etc
-[x] Create JWTAuthenticationFilter class which is used for the filter purpose
-[x] Create SecurityFilterConfig class to define request processing logic
-[x] Create JwtRequest and JwtResponse class
-[x] Create JwtAuthenticationController to return the JwtResponse if everything works fine

