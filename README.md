# sample-app-for-datadog

## Env
* DATADOG_API_KEY: [Datadog API key]
* DD_LOGS_INJECTION: true
* DD_TRACE_SAMPLE_RATE: 1

## Do it only first time
`wget -O dd-java-agent.jar https://dtdg.co/latest-java-tracer`

## Build
`mvn install -Dmaven.test.skip=true`

## Run
`java -javaagent:/path/to/dd-java-agent.jar -jar thisapp.jar`

## Test
Send http request to your app.
* ex1) Output 1000 logs(1KB each) at the same time.
  - http://your_server_ip:18000/logging?total-logs=1000&each-size=1
* ex2) Output 1 log (256KB each) at the same time.
  - http://your_server_ip:18000/logging?total-logs=1&each-size=256
* ex2) Output 5000 logs (1MB each) at the same time.
  - http://your_server_ip:18000/logging?total-logs=5000&each-size=1024
