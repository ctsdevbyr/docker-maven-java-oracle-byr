FROM driv/docker-maven-java-oracle:latest
WORKDIR /usr/src/app
RUN mkdir /tmp/quantum-parent
COPY quantum-parent/ /tmp/quantum-parent
RUN mvn -B -f /tmp/quantum-parent/pom.xml clean install -Dmaven.repo.local=/root/.m2/repository