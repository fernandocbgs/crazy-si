package interfaces;

import java.util.ArrayList;

public interface ITestador {
	
	public void setRN(IRN redeNeural);
	public void testa(ArrayList<double []> entradasTreinamento, ArrayList<double []> saidasTreinamento);
	public double [] getErrosMape();

}
