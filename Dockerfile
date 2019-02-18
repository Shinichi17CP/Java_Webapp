# Install Maven
FROM openjdk:11-jdk
RUN apt-get update
RUN apt-get install -y maven

# Install pandoc & others
ENV PANDOC_VERSION "1.16.0.2"
RUN apt-get install -y haskell-platform
RUN apt-get install -y pandoc
RUN apt-get install -y texlive-xetex

# Copying source files
WORKDIR /webapp
COPY . /webapp

# Run maven
RUN mvn package

# Run application
CMD ["sh","target/bin/simplewebapp"]
