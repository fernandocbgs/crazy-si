/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package redesneuraissi;

/**
 *
 * @author Lucas
 */
public class Utils {
    public static double geraErroPercentualErro(int total, int erros)
    {
        return 100.0*((double)erros)/((double)total);
    }
}
