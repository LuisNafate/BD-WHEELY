package com.wheely.util;

/**
 * Clase para estandarizar las respuestas de la API
 * Proporciona un formato consistente para respuestas exitosas y de error
 */
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;
    private long timestamp;

    // Constructor vacío
    public ApiResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    // Constructor completo
    public ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    // Constructor para respuestas exitosas con datos
    public ApiResponse(String message, Object data) {
        this.success = true;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    // Constructor para respuestas exitosas sin datos
    public ApiResponse(String message) {
        this.success = true;
        this.message = message;
        this.data = null;
        this.timestamp = System.currentTimeMillis();
    }

    // Métodos estáticos para crear respuestas comunes

    /**
     * Crea una respuesta exitosa con datos
     * @param message Mensaje descriptivo
     * @param data Datos a retornar
     * @return ApiResponse exitosa
     */
    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(true, message, data);
    }

    /**
     * Crea una respuesta exitosa sin datos
     * @param message Mensaje descriptivo
     * @return ApiResponse exitosa
     */
    public static ApiResponse success(String message) {
        return new ApiResponse(true, message, null);
    }

    /**
     * Crea una respuesta de error
     * @param message Mensaje de error
     * @return ApiResponse de error
     */
    public static ApiResponse error(String message) {
        return new ApiResponse(false, message, null);
    }

    /**
     * Crea una respuesta de error con detalles adicionales
     * @param message Mensaje de error
     * @param errorDetails Detalles del error
     * @return ApiResponse de error con detalles
     */
    public static ApiResponse error(String message, Object errorDetails) {
        return new ApiResponse(false, message, errorDetails);
    }

    /**
     * Crea una respuesta de validación con errores específicos
     * @param validationErrors Array de errores de validación
     * @return ApiResponse de error de validación
     */
    public static ApiResponse validationError(String[] validationErrors) {
        ValidationErrorResponse errorData = new ValidationErrorResponse(validationErrors);
        return new ApiResponse(false, "Error de validación", errorData);
    }

    /**
     * Crea una respuesta de no encontrado (404)
     * @param resource Recurso que no se encontró
     * @return ApiResponse de recurso no encontrado
     */
    public static ApiResponse notFound(String resource) {
        return new ApiResponse(false, resource + " no encontrado", null);
    }

    /**
     * Crea una respuesta de no autorizado (401)
     * @return ApiResponse de no autorizado
     */
    public static ApiResponse unauthorized() {
        return new ApiResponse(false, "No autorizado", null);
    }

    /**
     * Crea una respuesta de prohibido (403)
     * @return ApiResponse de prohibido
     */
    public static ApiResponse forbidden() {
        return new ApiResponse(false, "Acceso prohibido", null);
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }

    /**
     * Clase interna para errores de validación
     */
    public static class ValidationErrorResponse {
        private String[] errors;
        private int errorCount;

        public ValidationErrorResponse(String[] errors) {
            this.errors = errors;
            this.errorCount = errors != null ? errors.length : 0;
        }

        public String[] getErrors() {
            return errors;
        }

        public void setErrors(String[] errors) {
            this.errors = errors;
            this.errorCount = errors != null ? errors.length : 0;
        }

        public int getErrorCount() {
            return errorCount;
        }

        public void setErrorCount(int errorCount) {
            this.errorCount = errorCount;
        }
    }
}