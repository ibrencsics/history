FROM ubuntu:14.04
RUN apt-get update && \
  apt-get install -y apache2 && \
  apt-get clean
ENV APACHE_LOG_DIR /var/log/apache2
EXPOSE 80
ADD public_html /var/www/html/
ADD 000-default.conf /etc/apache2/sites-available
RUN a2enmod proxy
ENTRYPOINT ["/usr/sbin/apache2ctl", "-D", "FOREGROUND"]
