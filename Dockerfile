FROM tomcat:latest
ADD target/mariuszspetitions.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
