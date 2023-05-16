package com.juanfran.accountsmanager.services;

import org.cipherLibrary.Ciphers.AESCipherLibrary;
import org.cipherLibrary.Ciphers.HashingLibrary;

import javax.crypto.SecretKey;

public final class CipherServiceProvider {
    private CipherServiceProvider(){}

    /**
     * Este método se encarga de cifrar en hexadecimal
     * los datos recibidos por parametro del método y
     * devolverá los datos cifrados
     * @param data
     * @return String
     */
    public static String hashToHex(String data){

        //  Hasheamos los datos
        byte[] bytesData = HashingLibrary.hash(data);

        //  Convertimos a hexadecimal
        return HashingLibrary.toHexString(bytesData);
    }

    /**
     * Este método nos permite obtener la clave simétrica
     * a través de una semilla hasheada en hexadecimal
     * @param semilla
     * @return String
     */
    public static SecretKey generateAsemetricKey(String semilla){

        //  Hasheamos en hexadecimal la semilla
        String hashSemillaHex = hashToHex(semilla);

        //  Utilizamos la semilla hasheada para crear la clave simétrica
        return AESCipherLibrary.generarClaveSecretaAES(hashSemillaHex);
    }

}