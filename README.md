# loyalty-api


## Quick Start

**Pre-requisites**

- Java 8
- Maven 3
- Docker


### Spring Boot + H2Database (Default)

This setup is ideal for quick prototyping and defining the domain model. H2 is
simply in-memory and the data does not survive an application restart.

If you would like to persist your data beyond an app restart for development
reasons, simply change the connection string in application.yml to point to a
file within the project path, or just provide a filename. This is done to avoid
permission issues.

> Please do not commit the file containing your dev data.

**In Terminal**
```bash
git clone https://voltorn@bitbucket.org/voltorn/loyalty-api.git

cd loyalty-api

mvn clean install spring-boot:run
```

**In Browser**

[http://localhost:8080/api/v1/swagger-ui.html](http://localhost:8080/api/v1/swagger-ui.html)


---


### Dockerized

This setup is ideal for simulating the DEV deployment(s). Use this at your own
discretion and make sure Docker is installed.

The ideal usage of the containerized setup is intended for debug, given
containers from a higher environment containing app deployment, data,
and so forth.

**In Terminal**
```bash
git clone https://voltorn@bitbucket.org/voltorn/loyalty-api.git

cd loyalty-api

mvn clean install docker:run
```

**In Browser**

[http://localhost:8080/api/v1/swagger-ui.html](http://localhost:8080/api/v1/swagger-ui.html)

## IDE

**Recommended Plugins**

IntelliJ Lombok Plugin
[https://plugins.jetbrains.com/plugin/6317-lombok-plugin](https://plugins.jetbrains.com/plugin/6317-lombok-plugin)



## Notes

Inside the top level POM the docker build skip is enabled, if you would like to switch to MySQL instead of the in-memory H2Database, change the following:

```xml
<docker.skip>true</docker.skip>
```

to

```xml
<docker.skip>false</docker.skip>
```

inside the POM and update the app settings inside application.yml accordingly.  


### Get Auth Token 

curl loyalty_api_web:XY7kmzoNzl100AAAXY7kmzoNzl100@albumevo.com:9080/api/v1/oauth/token -d grant_type=password -d username=john.doe -d password=jwtpass

#### Register New User:

POST: /api/v1/account/register
CONTENT-TYPE: application/json
BODY:
{
    "username":"adrian8",
    "email":"adrian1@test.com",
    "password":"adrian1",
    "firstName":"adrian1FN",
    "lastName":"adrian1LN"
}

#### Secure Method With Role
@RequestMapping(value ="/users", method = RequestMethod.GET)
@PreAuthorize("hasAuthority('ADMIN_USER')")
public List<User> getUsers(){
  return userService.findAllUsers();
}

#### Generate Signing Key 

```shell

keytool -genkeypair -alias auth_server_keys -keyalg RSA -keypass mypass -keystore auth_server_keys.jks -storepass mypass


```

#### Use Key In SecurityConfig.java

```java

  @Value("${security.signing-key-password}")
	private String signingKeyPassword;

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    KeyStoreKeyFactory keyStoreKeyFactory =
            new KeyStoreKeyFactory(
                    new ClassPathResource("auth_server_keys.jks"),
                    signingKeyPassword.toCharArray());
    converter.setKeyPair(keyStoreKeyFactory.getKeyPair("auth_server_keys"));
		return converter;
	}

``` 
