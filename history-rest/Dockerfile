FROM jetty
RUN mkdir -p /opt/app-data/history-neo4j-rest && \
    chown jetty /opt/app-data/history-neo4j-rest && \
    chgrp jetty /opt/app-data/history-neo4j-rest
ADD target/*.war /var/lib/jetty/webapps
ENTRYPOINT ["java", "-jar", "/usr/local/jetty/start.jar"]


