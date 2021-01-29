package com.yuukiyg.sample.datadogapp.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlogicResult {
	private String message;
	private String time;

	public BlogicResult() {
		message = "default";
		time = "default";
	}
}
