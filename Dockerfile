# Use uma imagem base do OpenJDK. Para Java 17, o sem a '-jre' é preferível.
FROM maven:3.9.6-openjdk-17

# Crie um diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo pom.xml para o diretório de trabalho
# Isso aproveita o cache do Docker se as dependências não mudarem
COPY pom.xml .

# Copie o código fonte da sua aplicação
COPY src ./src

# Build da aplicação Spring Boot
# -DskipTests para pular os testes durante o build
RUN mvn clean install -DskipTests

# Exponha a porta que sua aplicação Spring Boot usa
EXPOSE 8080

# Comando para rodar a aplicação quando o contêiner for iniciado
# O nome do JAR final será algo como 'ecommerce-api-0.0.1-SNAPSHOT.jar'
# Você pode verificar o nome exato no seu diretório 'target/' após um build local
# Ou, de forma mais genérica:
CMD ["java", "-jar", "target/*.jar"]