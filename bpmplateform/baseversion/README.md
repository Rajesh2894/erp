# jBPM 6.5
- - - -

## Installation with Wildfly

### Part 1 - Configure your server
1. Download [wildfly-10.1.0.Final](http://download.jboss.org/wildfly/10.1.0.Final/wildfly-10.1.0.Final.zip) 
2. Download [mysql-connector-java-5.1.6](http://central.maven.org/maven2/mysql/mysql-connector-java/5.1.6/mysql-connector-java-5.1.6.jar)
3. Unzip wildfly-10.1.0.Final.zip

#### DB Driver Configuration
1. Make directory “mysql\main”  under “WILDFLY_HOME\modules\system\layers\base\com\”; Ignore this step if folder is already available.
2. Copy driver file mysql-connector-java-5.1.6.jar into WILDFLY_HOME\modules\system\layers\base\com\mysql\main folder. 
3. Configure mysql connector details  in an xml configuration file named module.xml with content; Ignore this step if content is already available in xml file.

```
	<?xml version="1.0" encoding="UTF-8"?>
	<module xmlns="urn:jboss:module:1.1" name="com.mysql">
		<resources>
			<resource-root path="mysql-connector-java-5.1.6.jar"/>
		</resources>
		<dependencies>
			<module name="javax.api"/>
			<module name="javax.transaction.api"/>
		</dependencies>
	</module>
```
4. Go to standalone configuration folder (WILDFLY_HOME/standalone/configuration/) and edit **standalone-full.xml**. Add MySQL datasource under 'datasources' and driver under 'drivers' tags as given below.

```
	<datasources>
		<datasource jndi-name="java:jboss/datasources/ServiceDS" pool-name="ServiceMySqlDS"enabled="true"use-java-context="true">
			<connection-url>jdbc:mysql://<database Ip>:<database port>/<database name>
			</connection-url>
			<driver>mysql</driver>
			<pool>
				<min-pool-size>2</min-pool-size>
				<max-pool-size>500</max-pool-size>
			</pool>
			<security>
				<user-name><user Id></user-name>
				<password><password></password>
			</security>
		</datasource>
		
		<drivers>
			<driver name="mysql" module="com.mysql">
				<driver-class>com.mysql.jdbc.Driver</driver-class>
			</driver>
		</drivers>
	<datasources>
```

#### Configure users
Create following user in application realm. [Reference](https://docs.jboss.org/author/display/WFLY10/add-user+utility)

* User 1 - System user for application integration using REST call
	* name: SysAdmin 
	* password: Super@123
	* roles: user,rest-all
	
* User 2 - jBPM platform administrator for processes/rule changes or deployment, user management and to make any changes necessary.
	* name: admin 
	* password: [Secure Password]
	* roles: admin,analyst,kiemgmt,rest-all,kie-server
	
* User 3 - Business administrator.
	* name: Administrator 
	* password: [Secure Password]
	* roles: Administrators,user
	 
### Part 2 - Deploy applications

#### Database Configuration

* jBPM have it's own predefined set of database tables. These tables must be created before we deploy jBPM war file in to the server.
* There are two files provided [here](https://192.168.100.7/svn/ABM_SAAS_Product/trunk/jbpmplatform/baseversion/ddl-scripts/mysqlinnodb), one for creating tables and another for dropping tables.
* To create all tables import or run following SQL file in to the database configured with JNDI 'java:jboss/datasources/ServiceDS'
https://192.168.100.7/svn/ABM_SAAS_Product/trunk/jbpmplatform/baseversion/ddl-scripts/mysql5/mysql5-jbpm-schema.sql


#### jBPM Application Configuration
1. Copy kie-wb-distribution-wars-6.5.0.Final-wildfly10.war into WILDFLY_HOME/standalone/deployments, while copying rename it as kie-wb to simplify the context paths that will be used on application server.
2. Find persistence.xml under kie-wb.war/WEB-INF/classes/META-INF/. Find hibernate.dialect and change its value accordingly database configuration. 
	* For,  **MySql** database, dialect value should be **org.hibernate.dialect.MySQLDialect.**
	* Also, conform that in persistence.xml file jta-data-source should be same as configured in standalone-full.xml file.
	* jta-data-source to point to the newly created data source (JNDI name) for your data  base for ex:- **java:jboss/datasources/ServiceDS**.

```
	<persistence-unit transaction-type="JTA" name="org.jbpm.domain">
	<provider>org.hibernate.ejb.HibernatePersistence</provider>
	<jta-data-source>java:jboss/datasources/ServiceDS</jta-data-source>
	<mapping-file>META-INF/Taskorm.xml</mapping-file>
	<mapping-file>META-INF/JBPMorm.xml</mapping-file>
```

3. Find jboss-deployment-structure.xml under kie-wb.war/WEB-INF/. Add module under 'dependencies' tag as given below.

```
	<dependencies>
		<module name="com.abm.configuration.brms" />
	</dependencies>
```
4. Copy directory 'com' from https://192.168.100.7/svn/ABM_SAAS_Product/trunk/jbpmplatform/baseversion/customization/jboss_module_configuration and paste under WILDFLY_HOME/modules
5. Open file application.properties and update following properties with actual values to integare with MainetService and MainetBpmService
	* mainet.service.rest.url = [protocol]://[host]:[port]/MainetService/rest/
	* workflow.rest.url.base = [protocol]://[host]:[port]/mainetbpmservice/rest

#### MainetBpmService Application Configuration

* MainetBpmService can be deploy on same wildfly-10 server where jBPM is running.
* MainetBpmService will use same JBoss module which we have configured at 4th step of 'jBPM Application Configuration'.
	

#### Configure system properties
Following list of properties needs to be given to configure JVM tuning considerations and Jboss server system properties in WILDFLY_HOME/bin/**standalone.conf.bat**

* -Xms10240M -Xmx10240M -XX:MetaspaceSize=5120M -XX:MaxMetaspaceSize=5120M (Heap size can be vary according to usage in production)
* -verbose:gc -XX:+UseConcMarkSweepGC -Dsun.rmi.dgc.server.gcInterval=360000000 (Optional GC and GC logging)
* -Djboss.as.management.blocking.timeout=600 (Jboss server startup timeout)

```
set "JAVA_OPTS=-Xms10240M -Xmx10240M -XX:MetaspaceSize=5120M -XX:MaxMetaspaceSize=5120M -verbose:gc -XX:+UseConcMarkSweepGC -Dsun.rmi.dgc.server.gcInterval=360000000 -Djboss.as.management.blocking.timeout=600"
```

Following list of properties needs to be given to work smoothly for kie-wb with MainetBpmService

* -Dorg.kie.build.disable-project-explorer=true 
* -Dorg.kie.override.deploy.enabled=false 
* -Dorg.kie.task.insecure=true

#### Launching the server
Best way is to add system properties into startup command when launching Wildfly server. Go to WILDFLY_HOME/bin and issue following command and wait until the kie workbench comes up.
```
	standalone.bat -c standalone-full.xml -b [IPAddress] -bmanagement=[IPAddress] -Djboss.socket.binding.port-offset=10 -Dorg.kie.build.disable-project-explorer=true -Dorg.kie.task.insecure=true
```
* [Kie workbench](http://IPAddress:8090/kie-wb/)
* [Kie workbench documentation](https://docs.jboss.org/jbpm/release/6.5.0.Final/jbpm-docs/html/pt03.html)
* [Mainet processes repository](https://github.com/abmindiarepomanager/mainet-workflow)
* [Mainet rule repository](https://github.com/abmindiarepomanager/abm-kie-repo)




