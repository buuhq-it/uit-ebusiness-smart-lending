# docker/Dockerfile
FROM tomcat:9.0.104-jdk11-temurin

# Remove default ROOT app
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy flowable war files
COPY wars/flowable-rest.war /usr/local/tomcat/webapps/
COPY wars/flowable-ui.war /usr/local/tomcat/webapps/

# Copy PostgreSQL driver
COPY jars/postgresql-42.7.2.jar /usr/local/tomcat/lib/

EXPOSE 8080