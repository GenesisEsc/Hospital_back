package Util;

public class ValidadorCedula {

    public static boolean esCedulaValida(String cedula){
        //1.Validacion de formato y longitud
        if(cedula == null || !cedula.matches("\\d{10}")) {
            return false;
        }
        //2.Validacion de provincia (01 a 24)
        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            return false;
        }
        //3.Validacion del tercer digito
        int tercerDigito = Character.getNumericValue(cedula.charAt(2));
        if (tercerDigito >= 6) {
            return false;
        }
        //4.Algoritmo modulo 10
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int suma= 0;

        for(int i = 0; i<9; i++){
            int valor = Character.getNumericValue(cedula.charAt(i)) * coeficientes[i];
            if (valor >= 10) valor -= 9;
            suma += valor;
        }
        int digitoVerficador = Character.getNumericValue(cedula.charAt(9));
        int decenaSuperior = ((suma + 9)/10)*10;
        int resultado = decenaSuperior - suma;

        if (resultado == 10) resultado = 0;

        return resultado == digitoVerficador;
    }
}
