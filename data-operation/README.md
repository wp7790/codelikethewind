# Data Operations in JBPM

# Purpose
An easy way to acceess, manipulate serialized data in jbpm

# Instruction for building and running the application

1) Build the jar
    ```sh
    $ mvn clean package
    ```

2) Run the application 
    ```sh
    $ mvn spring-boot:run
    ```
    **The following system properties are used:**
    * **thread.pool.size** : the number of threads that the program (**Default:** 10)
    * **db.url**: The database url
    * **db.user** : The user for connecting to the database
    * **db.password** : the user password


# What this project does

1. Deploys a kjar (packaged artifact of your workflows/rules
2. Connects to a db of your choosing (currently PostGres)
3. Give you access to read or modify data that would otherwise be serialized in the database



