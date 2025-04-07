FROM gcc
RUN apt-get update
RUN apt-get install -y netcat-openbsd
RUN apt-get install -y default-jdk
# netstat and other net tools (arp, route, ...)
RUN apt-get install -y net-tools
# ss and nsenter 
RUN apt-get install -y iproute2

WORKDIR /workspace
