# AlumniHub

## how to run
requirements: 
1. docker

run: 
1. startup postgreSql(by docker)
```bash
docker run \
--name alumnihub \
-p 5432:5432 \
-e POSTGRES_PASSWORD=postgres \
-d postgres
```

2. startup axon server(by docker)
```bash
docker run -d \
  --name axonserver \
  -p 8024:8024 \
  -p 8124:8124 \
  axoniq/axonserver:2023.1.1-jdk-17-dev
```

3. run application
```bash
./gradlew bootRun
```