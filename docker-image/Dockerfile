FROM adoptopenjdk:slim

EXPOSE 8090

RUN apt-get update && \
  apt-get install wget

RUN wget https://github.com/cloudfoundry/bosh-cli/releases/download/v6.2.1/bosh-cli-6.2.1-linux-amd64 -O /usr/local/bin/bosh
RUN chmod +x /usr/local/bin/bosh