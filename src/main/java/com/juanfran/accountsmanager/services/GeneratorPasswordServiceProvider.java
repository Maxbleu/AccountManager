package com.juanfran.accountsmanager.services;


import java.security.SecureRandom;
import java.util.Random;

public final class GeneratorPasswordServiceProvider {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+~`|}{[]:;?><,./-=";

    private GeneratorPasswordServiceProvider() {}

    private static char getChar(){
        Random random = new Random();
        char character = '0';

        int option = random.nextInt(2)+1;
        switch (option){
            case 1:
                    character = CHAR_LOWER.charAt(random.nextInt(CHAR_LOWER.length()));
                break;

            case 2:
                    character = CHAR_UPPER.charAt(random.nextInt(CHAR_UPPER.length()));
                break;
        }
        return character;
    }

    /**
     * Este método se encarga de generar contraseñas
     * en base principalmente a la longitud,
     * si requiere de letras, números y símbolos
     * @param lenght
     * @param putLettlers
     * @param putNumbers
     * @param putSimbols
     * @return
     */
    public static String generatePassword(Double lenght, boolean putLettlers, boolean putNumbers, boolean putSimbols){
        String password = "";
        String caracteres = "";

        if (putLettlers) {
            caracteres += "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        }
        if (putNumbers) {
            caracteres += "0123456789";
        }
        if (putSimbols) {
            caracteres += "!@#$%^&*()_+";
        }

        if(caracteres.length()>0){
            for (int i = 0; i < lenght; i++) {
                int index = (int) (Math.random() * caracteres.length());
                password += caracteres.charAt(index);
            }
        }

        return password;
    }
}