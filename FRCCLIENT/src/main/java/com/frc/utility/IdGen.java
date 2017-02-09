package com.frc.utility;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author songbin
 */
public class IdGen {
    private static AtomicLong id = new AtomicLong(System.currentTimeMillis());

    private IdGen() {
    };

    public static long getId() {
	return id.getAndIncrement();
    }
}
