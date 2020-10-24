package com.hotel.management.util;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.ArrayList;
import java.util.List;

public class UniqueIdGenerator {
	
	private static final long twepoch = 1288834974657L;
	private static final long sequenceBits = 17;
	private static final long sequenceMax = 65536;
	private static volatile long lastTimestamp = -1L;
	private static volatile long sequence = 0L;

	public static String userIDGenerator(String name) {

		String id = name + generateLongId();
		return id;
	}

	public static void main(String[] args) {
		List<Long> uniqueIds = new ArrayList<Long>();
		long now = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			String id = "User" + generateLongId();
			System.out.println(id);
			uniqueIds.add(generateLongId());

		}

		System.out.println("Number of Unique IDs generated: " + uniqueIds.size() + " in "
				+ (System.currentTimeMillis() - now) + " milliseconds");
	}

	private static synchronized Long generateLongId() {
		long timestamp = System.currentTimeMillis();
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) % sequenceMax;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0;
		}
		lastTimestamp = timestamp;
		Long id = ((timestamp - twepoch) << sequenceBits) | sequence;
		return id;
	}

	private static long tilNextMillis(long lastTimestamp) {
		long timestamp = System.currentTimeMillis();
		while (timestamp <= lastTimestamp) {
			timestamp = System.currentTimeMillis();
		}
		return timestamp;
	}

}
