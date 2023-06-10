package com.juanfran.accountsmanager.services;

import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import org.apache.log4j.Logger;

import java.security.SecureRandom;

public final class GeneratorPasswordServiceProvider {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+~`|}{[]:;?><,./-=";
    private static final String ALL_CHARACTERS = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARACTERS;

    private GeneratorPasswordServiceProvider() {}

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
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(lenght.intValue());

        if(putLettlers){
            password.append(CHAR_LOWER.charAt(random.nextInt(CHAR_LOWER.length())));
            password.append(CHAR_UPPER.charAt(random.nextInt(CHAR_UPPER.length())));
        }

        if(putNumbers){
            password.append(NUMBER.charAt(random.nextInt(NUMBER.length())));
        }

        if(putSimbols){
            password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));
        }

        for (int i = 4; i < lenght; i++) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        char[] passwordArray = password.toString().toCharArray();
        for (int i = password.length() - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = passwordArray[index];
            passwordArray[index] = passwordArray[i];
            passwordArray[i] = temp;
        }

        return new String(passwordArray);
    }

}
