package com.base.frame.common.tools.system;

import java.util.UUID;

public class ToolIdWorker {
    /**
     * 主机和进程的机器码
     */
    private static final ToolSequence worker = new ToolSequence();

    public static long getId() {
        return worker.nextId();
    }

    public static String getIdStr() {
        return String.valueOf(worker.nextId());
    }

    /**
     * <p>
     * 获取去掉"-" UUID
     * </p>
     */
    public static synchronized String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
