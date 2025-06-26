# Usando imagem oficial do OpenJDK 21 (altere se precisar)
FROM eclipse-temurin:21-jdk-alpine

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o pom.xml e o mvnw para aproveitar cache do Docker
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Instala as dependências e baixa o projeto (cache de dependências)
RUN ./mvnw dependency:go-offline -B

# Copia todo o código para o container
COPY src ./src

# Builda o projeto
RUN ./mvnw clean package -DskipTests

# Expõe a porta que o Spring Boot usará
EXPOSE 8080

# Comando para rodar o jar gerado
CMD ["java", "-jar", "target/Raspr0_-0.0.1-SNAPSHOT.jar"]
