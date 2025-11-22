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

    /**
     * Este método permite obtener un array
     * de bytes se encuentra dentro de la
     * cadena de carácteres
     * @param stringArrayBytes
     * @return
     */
    public static byte[] stringOfArrayBytesToArrayBytes(String stringArrayBytes){
        String[] elementos = stringArrayBytes.split(", ");

        byte[] arregloBytes = new byte[elementos.length];

        for (int i = 0; i < elementos.length; i++) {

            String number = "";

            if(i==0 || i==elementos.length-1){

                char[] chars = elementos[i].toCharArray();

                for(int j = 0; j<chars.length; j++){
                    if(chars[j]!='[' && chars[j]!=']'){
                        number = number + chars[j];
                    }
                }

            }else{

                number = elementos[i].trim();

            }

            arregloBytes[i] = Byte.parseByte(number);
        }

        return arregloBytes;
    }
}
