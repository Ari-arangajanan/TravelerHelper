package com.MITProject.TravelerHelperBot.botService;import org.springframework.stereotype.Component;import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;import org.telegram.telegrambots.meta.api.methods.send.SendMessage;import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;import java.util.ArrayList;import java.util.List;@Componentpublic class TelegramBotChats extends InlineBtnMarkUp{    SendMessage sendMessage = new SendMessage();    SendPhoto sendPhoto = new SendPhoto();    SendAnimation sendAnimation = new SendAnimation();    public SendMessage sendWelcomeMessage(Long chatId) {        sendMessage.setChatId(chatId.toString());        sendMessage.setText("Welcome to the Travel Assistant Bot!");        List<String> btnLbl = List.of("User", "Service Provider","back");        List<String> btnCallBack  = List.of("user", "service_provider","back");        InlineKeyboardMarkup inlineBtnMarkUp = inlineKeyboardMarkup(btnLbl, btnCallBack);        sendMessage.setReplyMarkup(inlineBtnMarkUp);        return sendMessage;    }}