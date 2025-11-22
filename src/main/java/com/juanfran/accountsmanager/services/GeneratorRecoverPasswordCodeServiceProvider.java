package com.juanfran.accountsmanager.services;

import java.security.SecureRandom;

public final class GeneratorRecoverPasswordCodeServiceProvider {
    private GeneratorRecoverPasswordCodeServiceProvider(){}

    /**
     * Este método se encarga generar claves
     * de recuperación de contraseñas
     * @return Integer
     */
    public static Integer getRecoverPasswordCode(){
        SecureRandom random = new SecureRandom();
        StringBuilder recoverCode = new StringBuilder();
        for(int i = 0; i<5; i++){
            byte[] numberGenerated = new byte[20];
            random.nextBytes(numberGenerated);
            recoverCode.append(String.valueOf((numberGenerated[0] & 0xFF) % 10 + 1));
        }
        return Integer.parseInt(recoverCode.toString());
    }
}
