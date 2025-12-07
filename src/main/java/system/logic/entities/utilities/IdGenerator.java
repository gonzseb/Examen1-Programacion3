package system.logic.utilities;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger projectCounter = new AtomicInteger(1);
    private static final AtomicInteger taskCounter = new AtomicInteger(1);
    private static final AtomicInteger userCounter = new AtomicInteger(1);

    public static String newProjectId() {
        return "PRJ-" + projectCounter.getAndIncrement();
    }

    public static String newTaskId() {
        return "TSK-" + taskCounter.getAndIncrement();
    }

    public static String newUserId() {
        return "USR-" + userCounter.getAndIncrement();
    }
}