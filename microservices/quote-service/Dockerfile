FROM openjdk:8
ADD target/quote-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# docker build -t quote-service . --platform linux/amd64

# Only when we want to push
# docker tag quote-service:latest 370116494334.dkr.ecr.us-east-1.amazonaws.com/snippet-tree:latest
# docker push 370116494334.dkr.ecr.us-east-1.amazonaws.com/snippet-tree:latest