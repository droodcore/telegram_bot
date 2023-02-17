package ru.kortukov.service;


import org.telegram.telegrambots.meta.api.objects.Message;
import ru.kortukov.entity.AppDocument;

public interface FileService {
    AppDocument processDoc(Message externalMessage);
}
