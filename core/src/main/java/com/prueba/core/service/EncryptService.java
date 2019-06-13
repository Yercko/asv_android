package com.prueba.core.service;

import android.util.Base64;

import java.security.AlgorithmParameters;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptService {

    private static int pswdIterations = 1000;
    private static int keySize = 256;
    private static int saltlength = keySize / 8;

    /***
     * Encripta el texto pasado como parámetro mediante el password
     * @param plainText Texto a encriptar
     * @param password Password para realizar la encriptación
     * @return Devuelve el texto encriptado junto con el salto y los IV's utilizados
     * @throws Exception
     */
    public String encrypt(String plainText, String password) throws Exception {

        //get salt
        String salt = generateSalt();
        byte[] saltBytes = salt.getBytes("UTF-8");

        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                saltBytes,
                pswdIterations,
                keySize
        );

        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        //encrypt the message
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

        // Base64 for Android
        String encodedText = Base64.encodeToString(encryptedTextBytes, Base64.DEFAULT);
        String encodedIV = Base64.encodeToString(ivBytes, Base64.DEFAULT);
        String encodedSalt = Base64.encodeToString(saltBytes, Base64.DEFAULT);

        String encodedPackage = encodedSalt + "]" + encodedIV + "]" + encodedText;
        return encodedPackage;
    }

    /**
     * Desencripta el texto pasado como parámetro con el password
     *
     * @param encryptedText
     * @param password
     * @return Devuelve nulo si el password no es correcto o el texto descencriptado si el
     * password es correcto
     * @throws Exception
     */
    public String decrypt(String encryptedText, String password) throws Exception {

        String[] fields = encryptedText.split("]");
        byte[] saltBytes = Base64.decode(fields[0], Base64.DEFAULT);
        byte[] ivBytes = Base64.decode(fields[1], Base64.DEFAULT);
        byte[] encryptedTextBytes = Base64.decode(fields[2], Base64.DEFAULT);

        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                saltBytes,
                pswdIterations,
                keySize
        );

        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        // Decrypt the message
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));

        try {
            byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
            return new String(decryptedTextBytes);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        } catch (BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[saltlength];
        random.nextBytes(bytes);
        return new String(bytes);
    }
}
