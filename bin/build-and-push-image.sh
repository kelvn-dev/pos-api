mvn clean install -DskipTests
docker build -t kelvn/vinpos-api:latest -f Dockerfile .
docker push kelvn/vinpos-api:latest