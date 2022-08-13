package com.example.backend.service.messaging;

import com.example.backend.dto.messaging.Note;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushFcmOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    public void sendNotifications(Note note, List<String> tokens) {
        try {
            firebaseMessaging.sendMulticast(prepareMessage(note, tokens));
        } catch (FirebaseMessagingException e) {
            log.error("Error on sending notifications", e);
        }
    }

    private MulticastMessage prepareMessage(Note note, List<String> tokens) {
        var notification = Notification
                .builder()
                .setTitle(note.getSubject())
                .setBody(note.getContent())
                .build();

        return MulticastMessage.builder()
                .addAllTokens(tokens)
                .setWebpushConfig(WebpushConfig.builder()
                        .setFcmOptions(WebpushFcmOptions.builder()
                                .setLink(note.getLink())
                                .build())
                        .build())
                .setNotification(notification)
                .putAllData(note.getData())
                .build();
    }
}
