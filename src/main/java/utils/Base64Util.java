package utils;

import java.util.Base64;

public class Base64Util {

    // Método para codificar una imagen (o cualquier byte[]) en Base64
    public static String encodeImagen(byte[] imagen) {
        return Base64.getEncoder().encodeToString(imagen);
    }
}