FROM ubuntu:14.04
RUN apt-get update && \
  apt-get install -y apache2 && \
  apt-get clean
ENV APACHE_LOG_DIR /var/log/apache2
EXPOSE 80
ADD . /var/www/html/
ENTRYPOINT ["/usr/sbin/apache2ctl", "-D", "FOREGROUND"]
