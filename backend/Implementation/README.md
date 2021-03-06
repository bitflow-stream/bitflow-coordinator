# Usage of swagger code generation

It is possbible to generate the projeect online under
* http://editor2.swagger.io/#!/

However that is not recommended as one has no control over package name, artifact- and group-id.
So what we should use is the swagger-cli which can be downloaded here
* https://mvnrepository.com/artifact/io.swagger/swagger-codegen-cli/2.2.3

Once downloaded use it as follows:

```shell
java -jar .\swagger-codegen-cli-2.2.3.jar generate -i swagger.json --group-id cit.bitflow.backend --artifact-id bitflow-backend-api --artifact-version 1.0.0 --api-package de.cit.backend.api --model-package de.cit.backend.api.model -l jaxrs-resteasy -o bitflow-backend-api
```

* i specifies the swagger schema-file
* o specifies the output directory

In order to run the server with jetty (like described in the generated README file) you have to remove the scope-attribute from 'javax.validation' in the pom file

Analog to this, we can generate the agent API by running:

```shell
java -jar .\swagger-codegen-cli-2.2.3.jar generate -i swagger-agent.json --group-id cit.bitflow.backend --artifact-id bitflow-backend-agent-api --artifact-version 1.0.0 --api-package de.cit.backend.agent.api --model-package de.cit.backend.agent.api.model -l java -o bitflow-backend-agent-api
```

You can type

```shell
java -jar .\swagger-codegen-cli-2.2.3.jar help generate
```

to explore more options.

## Modifications to the generated backend-API

After generating all code for the backend-API we have to modify it, to access the backend-mgmt module.

### Add Dependencies
First add the following dependencies to the bitflow-backend-apis pom file (inside the dependencies-tag):


```shell
<dependency>
    <groupId>cit.bitflow.backend</groupId>
    <artifactId>bitflow-backend-mgmt</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>javax</groupId>
    <artifactId>javaee-api</artifactId>
    <version>7.0</version>
    <scope>provided</scope>
</dependency>
```

### Include Mgmt-EJBs
After that we can include the mgmt-module. The only classes to be modified should be these inside src/main/java/de/cit/backend/api/impl.
This package should include classes named like 'UserApiServiceImpl'. Since the mgmt-module is using EJB-API, we will inject the business logic using a lookup.

Implement a Constructor to the ApiServiceImpl which looks like the following:

```java
    protected IUserService userService;
	
    public UserApiServiceImpl() {
        Context ctx;
        try {
            ctx = new InitialContext();
            userService = (IUserService)ctx.lookup("java:module/UserService");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
```

The corresponding EJB inside the mgmt-module then should be defnied like this:

```java
@Stateless
@Local(IUserService.class)
public class UserService implements IUserService {

```
and its interface

```java
@Local
public interface IUserService {
```

## Additional Configurations

### Wildfly

In order to configure the persistence context, you'll have to apply the following changes to your wildfly instance.
First open the `standalone.xml` inside `standalone/configuration` and search for the datasource subsystem.
Add the following lines:

```xml
<datasources>
	...
	<datasource jndi-name="java:jboss/datasources/CitDS" pool-name="CitDS">
		<connection-url>jdbc:mysql://10.200.2.71:3306/citBitDB?useSSL=false</connection-url>
        <driver>mysql</driver>
        <pool>
			<min-pool-size>10</min-pool-size>
			<max-pool-size>20</max-pool-size>
			<prefill>true</prefill>
        </pool>
        <security>
			<user-name>admin</user-name>
            <password>admin</password>
        </security>
    </datasource>
    <drivers>
		<driver name="mysql" module="com.mysql.driver">
			<driver-class>com.mysql.jdbc.Driver</driver-class>
		</driver>
		...
	</drivers>
</datasources>
```

Then you have to provide the jdbc driver for that datasource.
Inside the `modules`-folder of your wildfly installation create the path `system\layers\base\com\mysql\driver\main`.
Drop the jdbc jar file in here. We are using MySQL, please use `mysql-connector-java-5.1.38.jar`
(you can download it [here](https://mvnrepository.com/artifact/mysql/mysql-connector-java/5.1.38)).
Additionaly create a file named `module.xml` with the following content:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.5" name="com.mysql.driver">
	<resources>
		<resource-root path="mysql-connector-java-5.1.38.jar" />
	</resources>
	<dependencies>
		<module name="javax.api"/>
		<module name="javax.transaction.api"/>
	</dependencies>
</module>
```

The database access is now fully setup, you can use it in your applications persistence.xml by declaring:

```xml
<persistence-unit name="<your persistence name>">
		<provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <jta-data-source>java:jboss/datasources/CitDS</jta-data-source><!-- As defined in the standalone.xml -->
		
		<!-- ... additional properties -->
```

### Logging configuration

The wildfly already comes with build-in logging capabilities.
To configure a new logging class for the application, add the following to your `standalone.xml`:

```xml
<subsystem xmlns="urn:jboss:domain:logging:3.0">
	<periodic-rotating-file-handler name="APP_FILE" autoflush="true">
		<formatter>
			<named-formatter name="PATTERN"/>
        </formatter>
        <file relative-to="jboss.server.log.dir" path="application.log"/>
        <suffix value=".yyyy-MM-dd"/>
        <append value="true"/>
    </periodic-rotating-file-handler>
	
	<logger category="de.cit.backend">
		<level name="DEBUG"/>
		<handlers>
			<handler name="APP_FILE"/>
		</handlers>
    </logger>
	
	<!-- ... -->
	
</subsystem>
```

This creates a separate log file containing only those logs created by the application.
You should make use of the logger by calling

```java
private static final Logger log = Logger.getLogger(ProjectService.class);
log.info("Log message");
```


