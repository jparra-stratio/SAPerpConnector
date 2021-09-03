FROM openjdk:8

COPY ./out/artifacts/SAPerp/SAPjdbc.jar /tmp/SAPjdbc.jar

CMD ["java", "-jar", "/tmp/SAPjdbc.jar"]
