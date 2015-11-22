FROM java:8
VOLUME /tmp

ENV APP_HOME "/var/putput"

RUN useradd -d "$APP_HOME" -u 1100 -m -s /bin/bash putput && \
    mkdir /var/log/putput && \
    chown putput /var/log/putput

VOLUME /var/putput
VOLUME /var/log/putput

ENV JAVA_OPTS "-Djava.security.egd=file:/dev/./urandom -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled"
ENV APP_OPTS "-Dspring.profiles.active=production"
ENV APP_ARGS "--spring.config.location=/etc/putput/putput.properties"

ADD putput-server.jar app.jar
RUN bash -c 'touch /app.jar'

USER putput
ENTRYPOINT ["sh","-c","java $JAVA_OPTS $APP_OPTS -jar /app.jar $APP_ARGS"]
