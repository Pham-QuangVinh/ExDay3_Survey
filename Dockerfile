# syntax=docker/dockerfile:1

############################
# Stage 1: Build WAR (JDK 17)
############################
FROM maven:3.9.8-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy pom trước để cache dependencies
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

# Copy source và build
COPY src ./src
RUN mvn -B -DskipTests package

#################################
# Stage 2: Run on Tomcat (Jakarta)
#################################
FROM tomcat:10.1-jdk17

# Dọn webapps mặc định
RUN rm -rf /usr/local/tomcat/webapps/*

# Triển khai WAR vào root context để tránh 404 (/ -> app)
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# (Tuỳ chọn) đặt timezone khớp VN
ENV TZ=Asia/Ho_Chi_Minh

EXPOSE 8080
CMD ["catalina.sh", "run"]
