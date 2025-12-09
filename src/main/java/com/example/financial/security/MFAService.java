package com.example.financial.security;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class MFAService {

    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    // 1. Gerar secret para o usuário
    public String generateSecret() {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }

    // 2. Gerar URL para Google Authenticator
    public String getOtpAuthURL(String username, String secret, String issuer) {
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s",
                issuer, username, secret, issuer);
    }

    public String generateQrCodeBase64(String otpAuthURL) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        }catch (Exception e) {
            e.printStackTrace();
        }
        byte[] pngData = pngOutputStream.toByteArray();

        return Base64.getEncoder().encodeToString(pngData);
    }

    public boolean validateTotp(String secret, int code) {
        return gAuth.authorize(secret, code);
    }

}
