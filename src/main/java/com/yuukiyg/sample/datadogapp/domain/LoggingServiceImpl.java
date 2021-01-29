package com.yuukiyg.sample.datadogapp.domain;

import java.time.LocalDateTime;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import datadog.trace.api.Trace;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoggingServiceImpl implements LoggingService, InitializingBean {
	private static final int CHARACTERS_NUM_1KB_ASCII = 1024;
	private String[] stringArraySeparatedBy1Kb;

	@Override
	@Trace(operationName = "trace.blogic.logging", resourceName = "LoggingServiceImpl.logging")
	public BlogicResult logging(int totalLogs, int eachSize) {
		StringBuilder heavyStringBuilder = new StringBuilder(CHARACTERS_NUM_1KB_ASCII * eachSize);
		for (int i = 0; i < eachSize; i++) {
			heavyStringBuilder.append(stringArraySeparatedBy1Kb[i]);
		}
		final String heavyString = heavyStringBuilder.toString();

		for (int i = 0; i < totalLogs; i++) {
			log.info("loop={}, eachSize={}, string={}", i, eachSize, heavyString);
		}

		String msg = "logging done. totalLogs=" + totalLogs + ", eachSize=" + eachSize;
		return new BlogicResult(msg, LocalDateTime.now().toString());
	}

	@Override
	public void afterPropertiesSet() {
		// Holds 1MB string on memory in the state of separating 1KB string.
		this.stringArraySeparatedBy1Kb = generateStringArraySeparatedBy1Kb();
	}

	/**
	 * @return
	 */
	private String[] generateStringArraySeparatedBy1Kb() {
		final String str1mb = generate1MbString();
		String[] resultArray = new String[CHARACTERS_NUM_1KB_ASCII];

		// store 1KB substrings to array
		for (int i = 0, pointer = 0; i < CHARACTERS_NUM_1KB_ASCII; i++) {
			try {
				resultArray[i] = str1mb.substring(pointer, pointer + CHARACTERS_NUM_1KB_ASCII);
				pointer += CHARACTERS_NUM_1KB_ASCII;
			} catch (StringIndexOutOfBoundsException e) {
				// substringで切り出そうとすると窓サイズを超えた場合
				resultArray[i] = str1mb.substring(pointer);
				break;
			}
		}
		return resultArray;
	}

	/**
	 * generates 1MB string as following format<br>
	 * "a1b2c3d4e5f6g7h8i9j10k11l12m … 23x24y25z26a27b28c29d … "<br>
	 * @return
	 */
	private String generate1MbString() {
		final int MAX_TOTAL_SIZE = 1024 * 1024; // == 1MB: (1character=1byte) * 1024 * 1024
		StringBuilder sb = new StringBuilder(MAX_TOTAL_SIZE);
		final char[] alphabets = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		int alphabetIndex = 0;
		int totalSizeCount = 0;

		for (int i = 1;; i++) {
			// an alphabet and sequential number
			sb.append(alphabets[alphabetIndex]);
			sb.append(i);

			// break when the length of string reachs to max
			totalSizeCount += String.valueOf(i).length() + 1;
			if (totalSizeCount >= MAX_TOTAL_SIZE) {
				break;
			}

			if (alphabetIndex == alphabets.length - 1) {
				// return to 'a'
				alphabetIndex = 0;
			} else {
				alphabetIndex++;
			}
		}

		return sb.toString();
	}

}
