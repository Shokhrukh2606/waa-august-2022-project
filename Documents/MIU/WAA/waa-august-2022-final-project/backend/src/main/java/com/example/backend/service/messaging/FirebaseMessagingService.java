package com.example.backend.service.messaging;

import com.example.backend.dto.messaging.Note;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    public void sendNotifications(Note note, List<String> tokens) {
        firebaseMessaging.sendAllAsync(prepareMessages(note, tokens));
    }

    private List<Message> prepareMessages(Note note, List<String> tokens) {
        var notification = Notification
                .builder()
                .setTitle(note.getSubject())
                .setBody(note.getContent())
                .build();

        return tokens.stream().map(token ->
                Message.builder()
                        .setToken(token)
                        .setNotification(notification)
                        .putAllData(note.getData())
                        .build()).collect(Collectors.toList());
    }
}
