FROM ubuntu:latest

# Install.
RUN \
  sed -i 's/# \(.*multiverse$\)/\1/g' /etc/apt/sources.list && \
  apt-get update && \
  apt-get -y upgrade && \
  apt-get install -y build-essential && \
  apt-get install -y software-properties-common && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get upgrade -y
# Accept Oracle Java license
RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | debconf-set-selections
RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 seen true" | debconf-set-selections
# Install Oracle Java and other utilities
RUN \
  apt-get install -y oracle-java8-installer && \
  apt-get install -y byobu curl git htop man unzip vim wget && \
  rm -rf /var/lib/apt/lists/*

# Set environment variables
RUN \
  echo "JAVA_HOME=/usr/lib/jvm/java-8-oracle" >> /etc/environment

#expose ports
EXPOSE 8080
EXPOSE 8443

# Define default command.
CMD /bin/bash
