package com.base.frame.rabbitmq.own.constant;

public class ConstQueue {
    /**
     * 默认direct交换机队列
     */
    public static final String DIRECT_DEFAULT_QUEQUE = "order.direct.default.queue";
    /**
     * 默认fanout交换机队列1
     */
    public static final String FANOUT_DEFAULT_QUEUE_1 = "order.fanout.default.queue1";
    /**
     * 默认fanout交换机队列2
     */
    public static final String FANOUT_DEFAULT_QUEUE_2 = "order.fanout.default.queue2";
    /**
     * direct交换机延时消息队列(消息延时)
     */
    public static final String DIRECT_DELAY_M_QUEQUE = "order.direct.delay.m.queue";
    /**
     * direct交换机延时消息队列(队列延时)
     */
    public static final String DIRECT_DELAY_Q_QUEQUE = "order.direct.delay.q.queue";
    /**
     * 延时信息执行队列
     */
    public static final String DELAY_EXECUTE_QUEQUE = "order.delay.execute.queue";
}
