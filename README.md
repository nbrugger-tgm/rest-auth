# rest-auth

A Rest microservice for simple authentication and session management, meant to be used by bigger webservices.

### Usage

`gradle bootRun ` Starts the API on port 8080

### Features

* Registration
* Login
* Session Management

### Architecture

The interface is documented using OAS. The file is located in `src/main/resources/static/rest-auth.v1.json`

When the app is running you can have a look at the root path of the host to see a visual representation 

### Notes

There are no restrictions to the user IDs. This enables the using service to place constraints enforcing email or phone number format.

### Security measurements

As a session securing system `login-secure` from `nbrugger-tgm` is used, the most important features are:

* Banning of spamming IPs
* Marking accounts as "quarantined" when being attacked
* Prevention of brute forcing

### Persistence

The way persistence is used the data-source can be defined in the `application.properties`. For more information read the **spring data** documentation 