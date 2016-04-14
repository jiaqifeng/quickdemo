package com.jack.kafka.examples;

/**
 * Created by jack on 4/14/16.
 */
public interface MessageHandler<K, V> {
    boolean handle(K key, V message);
}
