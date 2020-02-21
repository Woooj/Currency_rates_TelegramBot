package com.example.telegabot.Bot;

import com.example.telegabot.XMLParser.SaxXMLParser;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {

    private static final String botName = "YourBotName";
    private static final String botToken = "YourToken";

    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        sendMessage.setText(getMessage(update.getMessage().getText()));
//        System.out.println(getMessage(update.getMessage().getText()));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }

    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getMessage(String msg) {
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowFirst = new KeyboardRow();
        KeyboardRow keyboardRowSecond = new KeyboardRow();


        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        if (msg.equals("USD")) {
            return getUSD();
        }

        if (msg.equals("EUR")) {
            return getEUR();

        }

        if (msg.equals("RUB")) {
            return getRUB();
        }

        keyboardRows.clear();
        keyboardRowFirst.clear();
        keyboardRowFirst.add("USD");
        keyboardRowFirst.add("EUR");
        keyboardRowSecond.clear();
        keyboardRowSecond.add("RUB");
        keyboardRows.add(keyboardRowFirst);
        keyboardRows.add(keyboardRowSecond);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        return "Выберите конвертируемую валюту";
    }

    public String getUSD() {
        try {
            return SaxXMLParser.getInfo("USD");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getEUR() {
        try {
           return SaxXMLParser.getInfo("EUR");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getRUB() {
        try {
            return SaxXMLParser.getInfo("RUB");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
