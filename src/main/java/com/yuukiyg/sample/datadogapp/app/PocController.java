package com.yuukiyg.sample.datadogapp.app;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuukiyg.sample.datadogapp.domain.BlogicResult;
import com.yuukiyg.sample.datadogapp.domain.LoggingService;

import datadog.trace.api.Trace;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Timed
@RequestMapping("/logging")
public class PocController {
	private LoggingService loggingService;
	private ModelMapper modelMapper;

	public PocController(LoggingService _loggingService, ModelMapper _modelMapper) {
		this.loggingService = _loggingService;
		this.modelMapper = _modelMapper;
	}

	/**
	 *
	 * When this endpoint calls, it outputs logs to stdout.
	 *
	 * @param totalLogs the number of total logs. 0 to 5000
	 * @param eachSize size of each logs(KB). 0 to 1024
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "custom-metrics.time.logging")
	@Trace(operationName = "trace.controller.logging", resourceName = "PocController.logging")
	public ControllerResult logging(
			@RequestParam(name = "total-logs", required = true) int totalLogs,
			@RequestParam(name = "each-size", required = true) int eachSize) {

		log.info("logging endpoint called!");

		// validation check
		if (totalLogs < 0 || 10000 < totalLogs) {
			String msg = "total-logs must be 0 <= tolal-logs <= 10000";
			log.error(msg);
			return new ControllerResult(msg, "");
		}
		if (eachSize < 0 || 1024 < eachSize) {
			String msg = "each-size must be 0 <= each-size <= 1024";
			log.error(msg);
			return new ControllerResult(msg, "");
		}

		BlogicResult blogicResult = loggingService.logging(totalLogs, eachSize);
		ControllerResult controllerResult = modelMapper.map(blogicResult, ControllerResult.class);

		log.info("logging: result={}", controllerResult);
		return controllerResult;
	}
}
