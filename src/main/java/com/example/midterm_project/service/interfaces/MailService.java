package com.example.midterm_project.service.interfaces;

import jakarta.mail.MessagingException;

public interface MailService {
    void sendVerificationEmail(String to, String verificationLink) throws MessagingException;
}
