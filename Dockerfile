# ==========================================
# Stage 1: Build stage (Kompilasi Aplikasi)
# ==========================================
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# Tentukan direktori kerja di dalam container
WORKDIR /app

# Salin pom.xml terlebih dahulu untuk download dependency (caching layer)
COPY pom.xml .

# Download dependency saja untuk mempercepat build berikutnya
RUN mvn dependency:go-offline -B

# Salin source code proyek
COPY src ./src

# Kompilasi aplikasi menjadi berkas .jar (abaikan testing karena database dinamis)
RUN mvn clean package -DskipTests

# ==========================================
# Stage 2: Run stage (Jalankan Aplikasi)
# ==========================================
FROM eclipse-temurin:21-jre-alpine

# Tentukan direktori kerja
WORKDIR /app

# Buat folder untuk penyimpanan upload file statis (desain dan bukti transfer)
RUN mkdir -p /storage

# Salin berkas .jar yang berhasil dibuat dari Stage 1
COPY --from=build /app/target/*.jar app.jar

# Ekspos port default (opsional sebagai dokumentasi, Render akan override ini)
EXPOSE 8080

# Jalankan aplikasi Spring Boot dengan port dinamis sesuai environment variable Render
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT:8080}"]