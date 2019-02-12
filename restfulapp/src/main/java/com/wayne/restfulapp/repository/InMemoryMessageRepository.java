package com.wayne.restfulapp.repository;

import com.wayne.restfulapp.model.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Service("messageRepository")
public class InMemoryMessageRepository implements MessageRepository {

    /**
     * 使用 ConcurrentHashMap 来模拟存储 Message 对象的增删改查，
     * AtomicLong 做为消息的自增组建来使用。
     * ConcurrentHashMap 是 Java 中高性能并发的 Map 接口，
     * AtomicLong 作用是对长整形进行原子操作，可以在高并场景下获取到唯一的 Long 值。
     */
    private static AtomicLong counter = new AtomicLong();
    private final ConcurrentMap<Long, Message> messages = new ConcurrentHashMap<>();

    @Override
    public List<Message> findAll() {
        List<Message> messages = new ArrayList<>(this.messages.values());
        return messages;
    }

    @Override
    public Message save(Message message) {
        Long id = message.getId();
        if (id == null) {
            id = counter.incrementAndGet();
            message.setId(id);
        }
        this.messages.put(id, message);
        return message;
    }

    @Override
    public Message update(Message message) {
        this.messages.put(message.getId(), message);
        return message;
    }

    @Override
    public Message updateText(Message message) {
        Message msg = this.messages.get(message.getId());
        msg.setText(message.getText());
        this.messages.put(message.getId(), msg);
        return message;
    }

    @Override
    public Message findMessage(Long id) {
        return this.messages.get(id);
    }

    @Override
    public void deleteMessage(Long id) {
        this.messages.remove(id);
    }
}
