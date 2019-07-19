package com.example.yun.wku;

import android.content.res.AssetManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class WKURSASecurity {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    private AssetManager assetManager;

    public WKURSASecurity(AssetManager assetManager) {
        this.assetManager = assetManager;
        loadRSAKey();
    }

    private void loadRSAKey() {
        try {

            InputStream ins = assetManager.open("wku_public.key");
            publicKey = getPublicKeyFromAssets(ins);

            InputStream ins2 = assetManager.open("wku_private_key.der");
            privateKey = getPrivateKeyFromAssets(ins2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] encryptRSA(String inputStr, PublicKey publicKey) throws Exception{
        byte[] encrypted = null;

        try {
            byte[] input  = inputStr.getBytes();

            Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encrypted = cipher.doFinal(input);

        } catch(Exception e){
            e.printStackTrace();
        }

        return encrypted;
    }

    public String decryptRSA(byte[] input, PrivateKey privateKey) throws Exception{
        byte[] decrypted = null;
        try{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decrypted = cipher.doFinal(input);
        }catch(Exception e){
            e.printStackTrace();
        }

        return new String(decrypted, "utf-8");
    }

    public static PublicKey getPublicKeyFromAssets(InputStream ins) throws Exception{

        byte[] keyBytes = _toByteArray(ins);

        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public static PrivateKey getPrivateKeyFromAssets(InputStream ins) throws Exception{

        byte[] keyBytes = _toByteArray(ins);

        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    private static byte[] _toByteArray(InputStream input) throws IOException {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[8192];

        while ((nRead = input.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }

    public PublicKey getPublicKey(){
        return publicKey;
    }
    public PrivateKey getPrivateKey(){
        return privateKey;
    }
}
