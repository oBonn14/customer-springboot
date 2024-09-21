# Gunakan image dasar dari OpenJDK
FROM openjdk:17-slim

# Set direktori kerja di dalam kontainer
WORKDIR /app

# Salin file JAR dari target ke dalam image
COPY target/api-customer-springboot-*.jar app.jar

# Jalankan aplikasi
ENTRYPOINT ["java", "-jar", "app.jar"]
