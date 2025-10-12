FROM openjdk:17-slim

ENV MAVEN_VERSION=3.8.7 \
    KOTLIN_VERSION=1.8.10 \
    PATH="/opt/maven/bin:/opt/kotlinc/bin:$PATH"

RUN apt-get update && \
    apt-get install -y curl unzip && \
    rm -rf /var/lib/apt/lists/*

RUN curl -fsSL https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.zip -o /tmp/maven.zip && \
    unzip /tmp/maven.zip -d /opt && \
    mv /opt/apache-maven-${MAVEN_VERSION} /opt/maven && \
    rm /tmp/maven.zip

RUN curl -fsSL https://github.com/JetBrains/kotlin/releases/download/v${KOTLIN_VERSION}/kotlin-compiler-${KOTLIN_VERSION}.zip -o /tmp/kotlin.zip && \
    unzip /tmp/kotlin.zip -d /opt && \
    mv /opt/kotlinc /opt/kotlinc-${KOTLIN_VERSION} && \
    ln -s /opt/kotlinc-${KOTLIN_VERSION} /opt/kotlinc && \
    rm /tmp/kotlin.zip

WORKDIR /project
