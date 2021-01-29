# sample-app-for-datadog

## Env
* DATADOG_API_KEY: [Datadog API key]
* DD_LOGS_INJECTION: true
* DD_TRACE_SAMPLE_RATE: 1

## Only first time
* `wget -O dd-java-agent.jar https://dtdg.co/latest-java-tracer`


## Build
 * `mvn install -Dmaven.test.skip=true`


## Run
* `java -javaagent:/path/to/dd-java-agent.jar -jar thisapp.jar`
