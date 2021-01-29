package com.yuukiyg.sample.datadogapp.app;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerResult {
	private String message;
	private String time;
}
