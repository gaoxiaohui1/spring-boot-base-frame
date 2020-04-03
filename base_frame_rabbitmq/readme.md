1、消息发送前得先有exchange（可以没有queue和binding）
2、消息发送只需要知道routingKey和要发送到哪个exchange
2.1、routingKey是必须的(可以是队列名(消费时队列名完全匹配)、可以有通配符：“*”,“#”（“*” 表示匹配一个单词， “#”则表示匹配没有或者多个单词)
2.2、不明确指定exchange的话会发送到默认的exchange(direct类型)
2.3、发送到fanout exchange的消息可以不需要routingKey，因为binding到这个exchange的queue都可以消费他的消息
3、消息接收前得有exchange、binding、queue
3.1、queue通过binding与exchange结合起来，通过queue名称与routingKey匹配来接收消息