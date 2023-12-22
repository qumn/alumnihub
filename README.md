# AlumniHub

## how to run
requirements: 
1. postgresql
2. docker

run: 
1. startup axon server(by docker)
```bash
docker run -d \
  --name axonserver \
  -p 8024:8024 \
  -p 8124:8124 \
  axoniq/axonserver:2023.1.1-jdk-17-dev
```
2. run application
```bash
./gradlew bootRun
```