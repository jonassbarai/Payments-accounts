package com.jonas.PaymentAccounts.service;

import com.jonas.PaymentAccounts.model.DTO.NotificatioresquestDTO;
import com.jonas.PaymentAccounts.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) {
        String email = user.getEmail();
        NotificatioresquestDTO notificatioresquestDTO = new NotificatioresquestDTO(email, message);

        ResponseEntity<String> response = restTemplate.postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", notificatioresquestDTO, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Erro ao Enviar Notificação");
            throw new RuntimeException("Service notification not working");
        }
    }

}
