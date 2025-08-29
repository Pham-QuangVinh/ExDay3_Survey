# ---- Stage 1: Build WAR ----
FROM maven:3.9.8-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B clean package -DskipTests

# ---- Stage 2: Run on Tomcat ----
FROM tomcat:10.1-jdk21

# Render đặt PORT (thường 10000)
ENV PORT=10000

# Xóa webapps mặc định
RUN rm -rf /usr/local/tomcat/webapps/*

# Đưa WAR làm ROOT.war để context = "/"
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Bắt Tomcat lắng nghe $PORT thay vì 8080
RUN sed -i 's/port="8080"/port="${PORT}"/' /usr/local/tomcat/conf/server.xml

CMD ["catalina.sh", "run"]
