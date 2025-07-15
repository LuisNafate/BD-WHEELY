package com.wheely.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utilidad para el manejo seguro de contraseñas
 * Utiliza BCrypt para el hashing y verificación de contraseñas
 */
public class PasswordUtil {

    // Nivel de complejidad para el hashing (12 es seguro pero puede ser lento, 10 es un buen balance)
    private static final int SALT_ROUNDS = 10;

    /**
     * Genera un hash seguro de una contraseña en texto plano
     * @param plainPassword Contraseña en texto plano
     * @return Hash de la contraseña
     * @throws IllegalArgumentException Si la contraseña es nula o vacía
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(SALT_ROUNDS));
    }

    /**
     * Verifica si una contraseña en texto plano coincide con un hash
     * @param plainPassword Contraseña en texto plano a verificar
     * @param hashedPassword Hash almacenado en base de datos
     * @return true si la contraseña coincide, false en caso contrario
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            // Si hay error en la verificación, retornar false por seguridad
            System.err.println("Error al verificar contraseña: " + e.getMessage());
            return false;
        }
    }

    /**
     * Valida si una contraseña cumple con criterios mínimos de seguridad
     * Requisitos:
     * - Mínimo 8 caracteres
     * - Al menos 1 letra mayúscula
     * - Al menos 1 letra minúscula
     * - Al menos 1 número
     * - Al menos 1 símbolo (#%$/"!?¿¡\)
     * @param password Contraseña a validar
     * @return true si la contraseña es válida, false en caso contrario
     */
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }

        // Criterio 1: Mínimo 8 caracteres
        if (password.length() < 8) {
            return false;
        }

        // Criterio 2: Al menos 1 letra mayúscula
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Criterio 3: Al menos 1 letra minúscula
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Criterio 4: Al menos 1 número
        if (!password.matches(".*[0-9].*")) {
            return false;
        }

        // Criterio 5: Al menos 1 símbolo especificado
        if (!password.matches(".*[#%$\"/\"!?¿¡\\\\].*")) {
            return false;
        }

        return true;
    }

    /**
     * Obtiene los requisitos de contraseña como texto
     * @return String con los requisitos de contraseña
     */
    public static String getPasswordRequirements() {
        return "La contraseña debe tener:\n" +
                "• Mínimo 8 caracteres\n" +
                "• Al menos 1 letra mayúscula\n" +
                "• Al menos 1 letra minúscula\n" +
                "• Al menos 1 número\n" +
                "• Al menos 1 símbolo (#%$/\"!?¿¡\\)";
    }

    /**
     * Valida y retorna detalles específicos de qué requisitos faltan
     * @param password Contraseña a validar
     * @return Array de strings con los requisitos que faltan, vacío si es válida
     */
    public static String[] getPasswordValidationErrors(String password) {
        if (password == null) {
            return new String[]{"La contraseña no puede ser nula"};
        }

        java.util.List<String> errors = new java.util.ArrayList<>();

        if (password.length() < 8) {
            errors.add("Debe tener al menos 8 caracteres");
        }

        if (!password.matches(".*[A-Z].*")) {
            errors.add("Debe tener al menos 1 letra mayúscula");
        }

        if (!password.matches(".*[a-z].*")) {
            errors.add("Debe tener al menos 1 letra minúscula");
        }

        if (!password.matches(".*[0-9].*")) {
            errors.add("Debe tener al menos 1 número");
        }

        if (!password.matches(".*[#%$\"/\"!?¿¡\\\\].*")) {
            errors.add("Debe tener al menos 1 símbolo (#%$/\"!?¿¡\\)");
        }

        return errors.toArray(new String[0]);
    }
}