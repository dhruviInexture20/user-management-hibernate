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


##### DataBase name = user-management-hbm


1. register user for admin from registration page

2. fire this update query


```
UPDATE `user-management-hbm`.`user` SET `role` = 'admin' WHERE (`userid` = '1');
```

