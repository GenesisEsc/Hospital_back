package Util;
/*
 * Autor: Génesis Escobar
 * Fecha: 06-12-2025
 * Versión: 1.0
 * Descripción:
 * Clase utilitaria encargada de validar cédulas ecuatorianas utilizando
 * el algoritmo oficial basado en módulo 10. Este validador se usa en la
 * capa de servicio antes de guardar o actualizar datos para garantizar
 * que el número de cédula ingresado sea real y cumpla con las reglas
 * establecidas por el Registro Civil.
 *
 * El metodo esCedulaValida realiza múltiples verificaciones: longitud,
 * provincia, tercer dígito y cálculo del dígito verificador.
 */

public class ValidadorCedula {

    /**
     * Valida si una cédula ecuatoriana es correcta utilizando reglas
     * formales y el algoritmo módulo 10.
     * @param cedula número de cédula recibido desde el usuario.
     * @return true si la cédula es válida, false en caso contrario.
     */
    public static boolean esCedulaValida(String cedula){
        // 1. Validación básica: no nula, solo dígitos y exactamente 10 caracteres
        if(cedula == null || !cedula.matches("\\d{10}")) {
            return false;
        }
        // 2. Validación del código de provincia (de 01 a 24)
        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            return false;
        }
        // 3. Validación del tercer dígito (0–5 para personas naturales)
        int tercerDigito = Character.getNumericValue(cedula.charAt(2));
        if (tercerDigito >= 6) {
            return false;
        }
        // 4. Algoritmo Módulo 10 usado para calcular el dígito verificador
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int suma= 0;

        // Multiplicación por coeficientes y suma ajustada
        for(int i = 0; i<9; i++){
            int valor = Character.getNumericValue(cedula.charAt(i)) * coeficientes[i];

            // Si el resultado es 10 o más, se resta 9 (regla del módulo 10)
            if (valor >= 10) valor -= 9;
            suma += valor;
        }
        // Cálculo del dígito verificador esperado
        int digitoVerficador = Character.getNumericValue(cedula.charAt(9));
        int decenaSuperior = ((suma + 9)/10)*10;
        int resultado = decenaSuperior - suma;

        // Si el resultado es 10, se reemplaza por 0 (regla del algoritmo)
        if (resultado == 10) resultado = 0;

        // Validación final: comparar con el dígito verificador real
        return resultado == digitoVerficador;
    }
}
