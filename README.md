# user-management

## Basic Configurations
tomcat-users.xml 

```
<role rolename="tomcat"/>
  <role rolename="manager-gui"/>
  <role rolename="manager-script"/>
  <role rolename="admin-gui"/>
  <user username="admin" password="admin" roles="manager-gui,manager-script,admin-gui,tomcat"/>
</tomcat-users>
```

maven> conf > settings.xml
```
<server>
      <id>TomcatServer</id>
      <username>admin</username>
      <password>admin</password>
</server>
```

## Maven Commands to run
```
mvn clean package
mvn tomcat7:deploy
```


## DataBase

### Table : userdata
```
CREATE TABLE `userdata` (
   `userid` int NOT NULL AUTO_INCREMENT,
   `fname` varchar(45) NOT NULL,
   `lname` varchar(45) NOT NULL,
   `gender` varchar(45) NOT NULL,
   `designation` varchar(45) NOT NULL,
   `email` varchar(45) NOT NULL,
   `phone` varchar(45) NOT NULL,
   `password` varchar(45) NOT NULL,
   `dob` date DEFAULT NULL,
   `role` varchar(45) NOT NULL DEFAULT 'user',
   `profilepic` blob,
   `question` varchar(45) DEFAULT NULL,
   `answer` varchar(45) DEFAULT NULL,
   `otp` varchar(45) DEFAULT NULL,
   PRIMARY KEY (`userid`)
 )
 
 ```
 Trigger
 ```
CREATE DEFINER=`root`@`localhost` TRIGGER `userdata_AFTER_DELETE` AFTER DELETE ON `userdata` FOR EACH ROW BEGIN
delete from user_address where userid=old.userid;
END
 ```
 
 ### Table : user_address
 
 
 ```
 CREATE TABLE `user_address` (
   `addressid` int NOT NULL AUTO_INCREMENT,
   `userid` int NOT NULL,
   `street_address` varchar(45) NOT NULL,
   `city` varchar(45) NOT NULL,
   `state` varchar(45) NOT NULL,
   `postal_code` varchar(45) NOT NULL,
   `country` varchar(45) NOT NULL,
   PRIMARY KEY (`addressid`)
 )
 ```
