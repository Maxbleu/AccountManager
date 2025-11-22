package com.juanfran.accountsmanager.interfaces;

public interface IGmailManager {
    void sendWelcomeEmail(String to);
    void sendRecoverPasswordCodeEmail(String to, Integer code);
}
