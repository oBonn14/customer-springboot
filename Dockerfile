# Gunakan image dasar dari OpenJDK
FROM openjdk:17-jre-slim

# Set direktori kerja di dalam kontainer
WORKDIR /app

# Salin file JAR dari target ke dalam image
COPY target/api-customer-springboot-*.jar app.jar

# Jalankan aplikasi
ENTRYPOINT ["java", "-jar", "app.jar"]
