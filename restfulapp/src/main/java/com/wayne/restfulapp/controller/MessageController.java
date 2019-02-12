package com.wayne.restfulapp.controller;

import com.wayne.restfulapp.model.Message;
import com.wayne.restfulapp.repository.MessageRepository;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    /**
     * 获取所有消息体
     * @return
     */
    @GetMapping(value = "messages")
    public List<Message> list() {
        List<Message> messages = this.messageRepository.findAll();
        return messages;
    }

    /**
     * 创建消息体
     * @param message
     * @return
     */
    @PostMapping(value = "message")
    public Message create(Message message) {
        message = this.messageRepository.save(message);
        return message;
    }

    /**
     * 使用put更新消息体
     * @param message
     * @return
     */
    @PutMapping(value = "message")
    public Message modify(Message message) {
        Message msgResult = this.messageRepository.update(message);
        return msgResult;
    }

    /**
     * 更新text字段
     * @param message
     * @return
     */
    @PatchMapping(value = "/message/text")
    public Message patch(Message message) {
        return this.messageRepository.updateText(message);
    }

    @GetMapping(value = "message/{id}")
    public Message get(@PathVariable Long id) {
       return this.messageRepository.findMessage(id);
    }

    @DeleteMapping(value = "message/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.messageRepository.deleteMessage(id);
    }

}
