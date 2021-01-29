# dd-high-load-test

## Environment vriables
* DATADOG_API_KEY: [Datadog API key]
* DD_LOGS_INJECTION: true

## Do this only first time
`wget -O dd-java-agent.jar https://dtdg.co/latest-java-tracer`

## Build
`mvn install -Dmaven.test.skip=true`  
This command needs jdk11.

## Run
`java -javaagent:/path/to/dd-java-agent.jar -jar thisapp.jar`  
Then, application will start on 18000 port.  
Note an datadog-agent is running on your environment.


## Test
Send http request to your app.
* ex1) Output 1000 logs(1KB each) at the same time.
  - http://your_server_ip:18000/logging?total-logs=1000&each-size=1
* ex2) Output 1 log (256KB each) at the same time.
  - http://your_server_ip:18000/logging?total-logs=1&each-size=256
* ex2) Output 5000 logs (1MB each) at the same time.
  - http://your_server_ip:18000/logging?total-logs=5000&each-size=1024
