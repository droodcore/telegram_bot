package ru.kortukov.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumerService {
    public void consumeTextMessageUpdates(Update update);
    public void consumeDocMessageUpdates(Update update);
    public void consumePhotoMessageUpdates(Update update);
}
