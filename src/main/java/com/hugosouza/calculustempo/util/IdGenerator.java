package com.hugosouza.calculustempo.util;

import java.util.concurrent.ThreadLocalRandom;

public final class IdGenerator {
    private static final long EPOCH = 1730000000000L;
    private static final long NODE_ID = ThreadLocalRandom.current().nextLong(0, 1024);
    private static final long NODE_ID_BITS = 10;
    private static final long SEQUENCE_BITS = 12;

    private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;

    private static long lastTimestamp = -1L;
    private static long sequence = 0L;

    private IdGenerator() {}

    public static synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards. Refusing to generate id.");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // Aguarda o próximo milissegundo
                while (currentTimestamp <= lastTimestamp) {
                    currentTimestamp = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        return ((currentTimestamp - EPOCH) << (NODE_ID_BITS + SEQUENCE_BITS))
                | (NODE_ID << SEQUENCE_BITS)
                | sequence;
    }
}
