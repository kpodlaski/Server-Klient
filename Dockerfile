FROM gcc
RUN apt-get update
RUN apt-get install -y netcat-openbsd
RUN apt-get install -y default-jdk
WORKDIR /workspace
