package robocode;

import interfaces.IMetodosJason;
import java.util.ArrayList;
import java.util.List;
import Matematica.CalculoVetores;

public class CalculosRoboCode {
	private IMetodosJason _imj;
	
	public CalculosRoboCode(IMetodosJason imj){_imj = imj;}
	
	public List<String> getOrdensSalvarRefem(){
		List<String> ordens = new ArrayList<String>();
		
		//double anguloCalculo = CalculoVetores.getAngulo(r1, r2);
		double qtdVirar = CalculoVetores.getQuantidadeVirar(_imj.getR1(), _imj.getR2());
//		System.out.println("anguloCalculo: " + anguloCalculo);
//		System.out.println("heading: " + r1.getHeading());
//		System.out.println("virar: " + qtdVirar);
		
		addOrdemVirar(ordens, qtdVirar);
		
		ordens.add("5");
		double distancia = getDistanciaRefem();
		
		//System.out.println("distancia: " + distancia);
		if (distancia <= 50) {
			ordens.add(distancia + "");
		}  else {
			
			if (distancia > 100) {
				ordens.add((distancia-50.0) + "");
			} else {
				ordens.add(50 + "");
			}
		}
		return ordens;
	}
	
	public List<String> getOrdensRefemSeguirRoboSalvador(){
		List<String> ordens = new ArrayList<String>();
		
		//double anguloRoboRefem = CalculoVetores.getAngulo(r2, r1);
		//double anguloPosicaoFinal = CalculoVetores.getAngulo(r1.getX(), r1.getY(), xF, yF);
		//double dif = Math.abs(anguloRoboRefem-anguloPosicaoFinal);
		
//		System.out.println("anguloRoboRefem: " + anguloRoboRefem);
//		System.out.println("anguloPosicaoFinal: " + anguloPosicaoFinal);
//		System.out.println("dif: " + dif);
		
		double qtdVirar = CalculoVetores.getQuantidadeVirar(_imj.getR2(), _imj.getR1());
		
		
		double distancia = getDistanciaRefem();
		double vlrSeguir = 0.0;
		//System.out.println("distancia: " + distancia);
		if (distancia > 100) {
			vlrSeguir = distancia-50.0;
		} else {
			if (distancia > 60.0) {
				vlrSeguir = 40.0;
			} else {
				vlrSeguir = -30.0; //dá a ré
			}
		}
		if (Math.abs(vlrSeguir) > 0.0) {
			//if (vlrSeguir > 0)
			addOrdemVirar(ordens, qtdVirar);
			ordens.add("5");
			ordens.add(vlrSeguir + "");
		}
		return ordens;
	}
	
	public List<String> getOrdensVoltarMinhaArea(){
		List<String> ordens = new ArrayList<String>();
		
		//tenho que verificar se não vou bater no robo refém (ou outros robos)
		//captura o angulo onde esta o robo refem 
//		double anguloRoboRefem = CalculoVetores.getAngulo(r1, r2);
//		double anguloPosicaoFinal = CalculoVetores.getAngulo(r1.getX(), r1.getY(), xF, yF);
//		double dif = anguloRoboRefem-anguloPosicaoFinal;
		
		//double distancia = CalculoVetores.distanciaPontos(r1.getX(), r1.getY(), r2.getX(), r2.getY());
		
		//System.out.println("dif: " + dif);

		double qtdVirar = CalculoVetores.getQuantidadeVirar(
				_imj.getR1().getX(), _imj.getR1().getY(), _imj.getXF(), _imj.getYF(), _imj.getR1().getHeading());
		
		//deve dar a ré se o robo estiver na sua frente
		//como ver que alguem esta no meu caminho ?
		//if (dif <= 50.0){
//		if (distancia <= 50.0){
//			System.out.println("#####dif: " + dif + ",qtdVirar: " + qtdVirar + 
//							   ",r1.h: " + r1.getHeading() + ",dis: " + distancia);
//		}
		
//		if (dif <= 50.0) {
//			addOrdemVirar(ordens, qtdVirar);
//			ordens.add("5");
//			ordens.add("" + (-30)); //ré
//		} else {
		
//		if (dif <= 50.0) {
//			qtdVirar = r1.getHeading() - anguloPosicaoFinal + (50.0);
//			//System.out.println("qtdVirar: " + qtdVirar );
//			
//			addOrdemVirar(ordens, qtdVirar);
//			
//			ordens.add("5");
//			ordens.add("" + 50.0);
//			
//		} else {
			addOrdemVirar(ordens, qtdVirar);
			ordens.add("5");
			ordens.add("" + CalculoVetores.distanciaPontos(_imj.getR1().getX(), _imj.getR1().getY(),_imj.getXF(), _imj.getYF()));
//		}
		
		return ordens;
	}
	
	public List<String> getOrdensLudibriarInimigo(){
//		List<String> ordens = new ArrayList<String>();
//		
//		double xRefem = _imj.getR2().getX();
//    	double yI = _imj.getR3().getY();
//    	double Ysalvador = _imj.getR1().getY();
//        if (yI <= 200) { //por baixo
//        	Ysalvador+=100;
//        } else { //por cima
//        	Ysalvador-=100;
//        }
//
//		double qtdVirar = CalculoVetores.getQuantidadeVirar(
//							_imj.getR1().getX(), _imj.getR1().getY(),  
//							xRefem, Ysalvador, _imj.getR1().getHeading()
//							);
//		addOrdemVirar(ordens, qtdVirar);
//		
//		ordens.add("5");
//		double distancia = getDistanciaInimigo();
//		
//		//System.out.println("distancia: " + distancia);
//		if (distancia <= 50) {
//			ordens.add(distancia + "");
//		}  else {
//			
//			if (distancia > 100) {
//				ordens.add((distancia-50.0) + "");
//			} else {
//				ordens.add(50 + "");
//			}
//		}
//		return ordens;
		
		return getOrdensSalvarRefem(); //por enquanto....
	}
	
	public List<String> getOrdensBloquearAgSave(){
		List<String> ordens = new ArrayList<String>();
		double qtdVirar = CalculoVetores.getQuantidadeVirar(
		_imj.getR3().getX(), _imj.getR3().getY(),  
		_imj.getR1().getX(), _imj.getR1().getY(), _imj.getR3().getHeading()
		);
		addOrdemVirar(ordens, qtdVirar);
		
		ordens.add("5");
		double distancia = getDistanciaInimigo();
		
		//System.out.println("distancia: " + distancia);
		if (distancia <= 50) {
			ordens.add(distancia + "");
		}  else {
			
			if (distancia > 100) {
				ordens.add((distancia-50.0) + "");
			} else {
				ordens.add(50 + "");
			}
		}
		return ordens;
	}
	
	public double getDistanciaInimigo() {
		return CalculoVetores.distanciaPontos(_imj.getR1().getX(), _imj.getR1().getY(),_imj.getR3().getX(), _imj.getR3().getY());
	}
	public double getDistanciaRefem() {
		return CalculoVetores.distanciaPontos(_imj.getR1().getX(), _imj.getR1().getY(),_imj.getR2().getX(), _imj.getR2().getY());
	}
	
	private void addOrdemVirar(List<String> ordens, double qtdVirar){
		if (
				!Matematica.CalculoVetores.Entre(qtdVirar, 0, 1.0) && 
				!Matematica.CalculoVetores.Entre(qtdVirar, 360, 1.0)
			) {
			ordens.add("3"); //virar esquerda
			ordens.add(""+ qtdVirar);
		}
		
	}
}
