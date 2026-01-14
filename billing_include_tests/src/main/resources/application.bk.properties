#This porpertie is mandatory on defautl .properties (file witout profile suffix)
spring.profiles.active=@spring.profiles.active@
server.port=7080
# Session store type.
spring.session.jdbc.initialize-schema=always
spring.session.timeout=900
#Dafult pgeabel values
spring.data.web.pageable.default-page-size=10
spring.data.web.pageable.max-page-size=50


#*************************************************************************
## Spring DATASOURCE (DataSourceAutoConfiguration & DataSourceProperties) 
#*************************************************************************
#H2 Database
spring.h2.console.enabled=true
#spring.datasource.url =jdbc:h2:mem:testdb
#spring.datasource.url=jdbc:h2:file:./h2data/testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=${DB_USERNAME:sa}
spring.datasource.password=${DB_PASSWORD:qwerty}
## JPA Properties
# The SQL dialect makes Hibernate generate better SQL for the chosen database
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto =update
spring.jpa.show-sql=true

#*************************************************************************
# Disabling the /v3/api-docs endpoint
#*************************************************************************
springdoc.api-docs.enabled=true
# Disabling the swagger-ui
springdoc.swagger-ui.enabled=true
# swagger-ui custom path
springdoc.swagger-ui.use-root-path=true
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.show-login-endpoint=true

springdoc.packagesToScan=com.paymentchain.billing.entities, com.paymentchain.billing.controller
springdoc.pathsToMatch=/v1, /billing/**

spring.security.user.name=${APP_USER:admin}
spring.security.user.password=${APP_USER:admin}
spring.security.user.roles=ADMIN