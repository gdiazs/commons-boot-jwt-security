# commons-boot-jwt-security

[![Build Status](https://travis-ci.org/gdiazs/commons-boot-jwt-security.svg?branch=master)](https://travis-ci.org/gdiazs/commons-boot-jwt-security)

[![enter image description here](https://sonarcloud.io/api/project_badges/measure?project=io.gdiazs.commons:commons-boot-jwt-security&metric=alert_status)](https://sonarcloud.io/dashboard?id=io.gdiazs.commons%3Acommons-boot-jwt-security)

It's an basic library to quickly enable JWT in a spring boot application. If you get here looking for jwt in spring boot you will notice that this implementation of jwt is very simple. I hope this help someone and of course I'll be happy to work on all your pull request that you could have. 

This project is compatible with spring boot security, so you can use annotation to configure access base on the authorities of your user as you can see in this [class](https://github.com/gdiazs/commons-boot-jwt-security/blob/master/src/test/java/io/gdiazs/commons/boot/security/authentication/PingController.java).

### Clone the repo, compile with maven 

    git clone https://github.com/gdiazs/commons-boot-jwt-security.git
    cd commons-boot-jwt-security
    mvn clean install

### On your spring boot project import the dependency

	
	<dependency>
		<groupId>io.gdiazs.commons</groupId>
		<artifactId>commons-boot-jwt-security</artifactId>
		<version>${commons-boot-jwt-security}</version>
	</dependency>

		
### In Spring Boot Main add this annotation

    @SpringBootApplication
    @EnableCommonsJwtSecurity
    public class DemoApplication {
    
    	public static void main(String[] args) {
    		SpringApplication.run(DemoApplication.class, args);
    	}
    }

### Create a SecurityConfig class and extends from CommonsWebSecurityAdapter

	@Configuration
	@EnableWebSecurity
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	public class SecurityConfig extends CommonsWebSecurityAdapter{

		@Override
		public void configure(WebSecurity web) throws Exception {

			super.configure(web);
			web.ignoring().antMatchers("/unsecure");
		}


	}


### Application properties file
You'll need a minimum configuration in your properties, a secret word in my case a hash from sha256 and a expiration value in seconds to the future. If you want that you token expires in 1hr just add 3600 seconds.

    #JWT security config
    token.secret= 872e4e50ce9990d8b041330c47c9ddd11bec6b503ae9386a99da8584e9bb12c4
    token.expiration=3600



### Be sure that you count with a database like this.
Of course you can extend it, its just a minum reference to create a users and auths (roles) in a tiny database,  

    CREATE TABLE authorities (
      id_authoritie integer NOT NULL,
      authority_description varchar(100) NOT NULL,
      authority_enabled integer NOT NULL,
      authority_name varchar(40) NOT NULL,
      created_at timestamp NOT NULL,
      updated_at timestamp NOT NULL,
      PRIMARY KEY (id_authoritie)
    );
    
    CREATE TABLE users (
      id_user integer NOT NULL,
      created_at timestamp NOT NULL,
      updated_at timestamp NOT NULL,
      user_account_non_expired integer NOT NULL,
      user_account_non_locked integer NOT NULL,
      user_credentials_non_expired integer NOT NULL,
      user_email varchar(50) NOT NULL,
      user_enabled integer NOT NULL,
      user_last_password_reset_date timestamp NOT NULL,
      user_name varchar(20) NOT NULL,
      user_password varchar(100) NOT NULL,
      PRIMARY KEY (id_user)
    );
    
    CREATE TABLE users_authorities (
      authoritie_id integer NOT NULL,
      user_id integer NOT NULL,
      created_at timestamp NOT NULL,
      updated_at timestamp NOT NULL,
      PRIMARY KEY (authoritie_id, user_id)
    );
    
    ALTER TABLE users_authorities ADD CONSTRAINT UK_p6riuboavq2dk38kq8at4w9te UNIQUE (authoritie_id);
    ALTER TABLE users_authorities ADD CONSTRAINT UK_6uk70o9gngtb60n4qb5sr0xsg UNIQUE (user_id);
    ALTER TABLE users_authorities ADD CONSTRAINT FKhpafpofndaokj07nj9lsd29sm FOREIGN KEY (authoritie_id) REFERENCES authorities;
    ALTER TABLE users_authorities ADD CONSTRAINT FKq3lq694rr66e6kpo2h84ad92q FOREIGN KEY (user_id) REFERENCES users;


### Boot the project and make a post call

http://localhost:port/auth


    { 
	  "username" : "yourUserName",
	  "password" : "yourPassword"
    }

Then you will get

    {
	  "token": "yourToken"
	}


![Request](https://i.imgur.com/ONiI4Ir.png)

### On each http request just as a http header
	X-Auth-Token: yourToken
	
	

## Thanks to
[spring-security-jwt-rest-stateless](https://github.com/lynas/spring-security-jwt-rest-stateless)

[jwt-spring-security-demo](https://github.com/szerhusenBC/jwt-spring-security-demo)


## License
commons-boot-jwt-security is released under version 2.0 of the [Apache License.](http://www.apache.org/licenses/LICENSE-2.0)


