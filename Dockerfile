# ---- Stage 1: Build WAR bằng Maven + JDK 17 ----
FROM maven:3.9.8-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy pom.xml và source
COPY pom.xml .
COPY src ./src

# Build WAR
RUN mvn -B clean package -DskipTests

# ---- Stage 2: Run với Tomcat 10 + JDK 17 ----
FROM tomcat:10.1-jdk17

# Render cung cấp PORT (thường 10000)
ENV PORT=10000

# Xóa webapp mặc định
RUN rm -rf /usr/local/tomcat/webapps/*

# Đưa WAR vào Tomcat, đổi tên thành ROOT.war để context = "/"
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Chỉnh Tomcat lắng nghe PORT của Render
RUN sed -i 's/port="8080"/port="${PORT}"/' /usr/local/tomcat/conf/server.xml

# Start Tomcat
CMD ["catalina.sh", "run"]
