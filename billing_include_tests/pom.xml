<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.3</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target> 
        <spring-boot.version>3.1.2</spring-boot.version>  
        <mapstruct.version>1.5.5.Final</mapstruct.version>
        <lombok.version>1.18.34</lombok.version>
        <springdoc-openapi>2.4.0</springdoc-openapi>     
        <maven.archetypep.version>3.2.1</maven.archetypep.version>
 
    </properties> 
       
    <groupId>com.paymentchain</groupId>
    <artifactId>billing</artifactId>
    <version>0.0.1</version>
    <packaging>jar</packaging>

    <name>billing</name>
    <description>Basic project for Spring Boot</description>      

    <dependencies>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>${springdoc-openapi}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>          
        </dependency>
        <dependency>           
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>       
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>           
        </dependency>  
              
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId> <!-- O la versión más reciente -->
        </dependency>   
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>           
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>${mapstruct.version}</version>   
        </dependency> 
        
        <!--Jakarta Bean Validation provider-->
        <dependency>
            <groupId>org.hibernate.validator</groupId>
            <artifactId>hibernate-validator</artifactId>
        </dependency>                              
    </dependencies>

    <!-- Add profiles for prod and local -->
    <profiles>
        <!-- local profile using h2-->
        <profile>
            <id>local</id>     
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <!-- Define properties for local environment -->
                <spring.profiles.active>local</spring.profiles.active>
            </properties>
        </profile>
        <!-- Local profile using postgres -->
        <profile>
            <id>prod</id>            
        </profile>
    </profiles>
    
    <build>       
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludeDevtools>false</excludeDevtools>
                </configuration>
            </plugin>
           
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>               
                <configuration>
                    <source>${maven.compiler.source}</source>
                    <target>${maven.compiler.source}</target>
                    <!-- generate mappers -->
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>${lombok.version}</version>
                        </path>
                        <path>
                            <groupId>org.mapstruct</groupId>
                            <artifactId>mapstruct-processor</artifactId>
                            <version>${mapstruct.version}</version>
                        </path> 
                    </annotationProcessorPaths>
                </configuration>
            </plugin>   
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-archetype-plugin</artifactId>
                <version>${maven.archetypep.version}</version>
                <configuration>
                    <outputDirectory>/home/sotobotero/workspace/repositories/personal/teaching_resources/archetypes/sotobotero-spring-boot-security-mapstruct-crud</outputDirectory>  
                    <propertyFile>archetype.properties</propertyFile>    
                </configuration>
                <executions>
                    <execution>
                        <id>archetype-create</id>
                        <goals>
                            <goal>create-from-project</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>          
        </plugins>
    </build>
</project>
