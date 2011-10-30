package servidor_tcp.gui;

import java.util.List;

public interface IServidorTCP {
	public void print(String msg); //imprime uma mensagem
	public List<String> getDadosRobo(); //lista com os dados do robo
	public void ExecutarAcoes(List<String> l); //robo executa as ações
}