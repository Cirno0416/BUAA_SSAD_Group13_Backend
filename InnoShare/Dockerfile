# 使用官方OpenJDK作为基础镜像
FROM openjdk:11-jre-slim

# 设置工作目录
WORKDIR /app

# 复制构建生成的jar文件到容器中
COPY target/InnoShare-1.0.0.jar app.jar

# 暴露应用端口（根据您的应用更改端口）
EXPOSE 8080

# 运行jar文件
ENTRYPOINT ["java", "-jar", "app.jar"]