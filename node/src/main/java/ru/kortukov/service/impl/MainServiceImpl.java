package ru.kortukov.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kortukov.dao.AppUserDAO;
import ru.kortukov.dao.RawDataDAO;
import ru.kortukov.entity.AppUser;
import ru.kortukov.entity.RawData;
import ru.kortukov.service.MainService;
import ru.kortukov.service.ProducerService;

import static ru.kortukov.entity.enums.UserState.BASIC_STATE;

@Service
public class MainServiceImpl implements MainService {

    private final RawDataDAO rawDataDAO;
    private final ProducerService producerService;
    private final AppUserDAO appUserDAO;

    public MainServiceImpl(RawDataDAO rawDataDAO, ProducerService producerService, AppUserDAO appUserDAO) {
        this.rawDataDAO = rawDataDAO;
        this.producerService = producerService;
        this.appUserDAO = appUserDAO;
    }

    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);

        var textMessage = update.getMessage();
        var telegramUser = textMessage.getFrom();
        var appUser = findOrSaveAppUser(telegramUser);

        var message = update.getMessage();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Hello from NODE");
        producerService.producerAnswer(sendMessage);
    }

    private AppUser findOrSaveAppUser(User telegramUser) {
        AppUser persistentAppUser = appUserDAO.findAppUserByTelegramUserId(telegramUser.getId());

        if (persistentAppUser == null) {
            AppUser transientAppUser = AppUser.builder()
                                            .telegramUserId(telegramUser.getId())
                                            .username(telegramUser.getUserName())
                                            .firstName(telegramUser.getFirstName())
                                            .lastName(telegramUser.getLastName())
                                            //TODO изменить значение по умолчанию после добавления регистрации
                                            .isActive(true)
                                            .state(BASIC_STATE)
                                            .build();
            return appUserDAO.save(transientAppUser);
        }
        return persistentAppUser;
    }

    private void saveRawData(Update update) {
        RawData rawData = RawData.builder()
                                 .event(update)
                                 .build();
        rawDataDAO.save(rawData);
    }
}
