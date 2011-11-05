package Matematica;

import robocode.DadosRobos.DadosRobos;

public class CalculoVetores {
    public static XY getVetor(XY p1, XY p2){
        return new XY(p1.X()-p2.X(), p1.Y()-p2.Y());
    }

    public static double multiplicao(XY v1, XY v2){
        return v1.X()*v2.X() + v1.Y()*v2.Y();
    }

    public static double normaPontos(XY p1, XY p2){
        return norma(getVetor(p1, p2));
    }
    public static double norma(XY v){
        return Math.sqrt(v.X()*v.X() + v.Y()*v.Y());
    }
    public static double distanciaPontos(XY p1, XY p2){
    	return distanciaPontos(p1.X(), p1.Y(), p2.X(), p2.Y());
    }
    /**
     * @return a distancia entre 2 pontos
     * */
    public static double distanciaPontos(double x1, double y1, double x2, double y2){
    	double xo = x2 - x1;
    	double yo = y2 - y1;
        return Math.sqrt(xo * xo + yo * yo);
    }
    
    public static double CalculoAnguloVetores(XY p1, XY p2, XY p3){
		XY v1 = CalculoVetores.getVetor(p1, p2);
		XY v2 = CalculoVetores.getVetor(p1, p3);
		
		//System.out.println("v1: " + v1 + ", v2: " + v2);

		double mult = CalculoVetores.multiplicao(v1, v2);
		double n1 = CalculoVetores.norma(v1);
		double n2 = CalculoVetores.norma(v2);
		double calc = mult / (n1 * n2);
		
//		System.out.println("mult: " + mult);
//		System.out.println("n1: " + n1 + ", n2: " + n2);
//		System.out.println("calc: " + calc);
		
		// o = arccos( u * v / |v|*|u| )
		double o = Math.acos(calc);
		//System.out.println("o1: " + o);
		o = Math.toDegrees(o);
		//System.out.println("o2: " + o);
    	return o;
    }
    
    public static double AnguloPontos(XY p11, XY p12, XY p21, XY p22){
        return AnguloVetores(getVetor(p11, p12), getVetor(p21, p22));
    }
    
    public static double AnguloVetores(XY v1, XY v2){
        double calc = (multiplicao(v1, v2) / (norma(v1)+ norma(v2)));
        return Math.acos(calc); //arcocosseno
    }
    
    //########################################################################
    public static double getAngulo(DadosRobos r1, DadosRobos r2){
    	return getAngulo(r1.getX(), r1.getY(), r2.getX(), r2.getY());
    }
    public static double getAngulo(double x1, double y1, double x2, double y2){
		XY px1 = new XY(x1, y1);
		XY px2 = new XY(x2, y2);
		XY px3 = new XY(x1, y2);
		Double angulo = CalculoVetores.CalculoAnguloVetores(px1,px2,px3);
		if (angulo.isNaN()) { /*System.err.println("angulo com valor invalido");*/ angulo = 0.0; }
		return angulo;
    }
    
    public static double getQuantidadeVirar(DadosRobos r1, DadosRobos r2, double heading){
    	return getQuantidadeVirar(r1.getX(), r1.getY(), r2.getX(), r2.getY(), heading);
    }
    /**
     * para o robo r1: CalculoVetores.getQuantidadeVirar(r1, r2)
     * para o robo r2: CalculoVetores.getQuantidadeVirar(r2, r1)
     * */
    public static double getQuantidadeVirar(DadosRobos r1, DadosRobos r2){
		return getQuantidadeVirar(r1.getX(), r1.getY(), r2.getX(), r2.getY(), r1.getHeading());
	}
    public static double getQuantidadeVirar(double x1, double y1, double x2, double y2, double heading){
		Double angulo = getAngulo(x1, y1, x2, y2);
		
		//System.out.println("[1] angulo: " + angulo);
		//System.out.println("calculo angulos x1: " + x1 + ", y1: " + y1 +", x2: " + x2 + ", y2: " + y2);
		if (x1 > x2 && y1 > y2) { angulo += 180.0;/*caso 1*/ }
		if (x1 < x2 && y1 > y2) { angulo += 90.0; /*caso 2*/} 
		if (x1 < x2 && y1 < y2) { /*angulo = angulo;*/ /*caso 3*/}
		if (x1 > x2 && y1 < y2) { /*angulo += 270.0;*/ angulo = -angulo; /*caso 4*/ }
		if (Entre(x1, x2) && y1 > y2) { angulo += 180.0; /*caso 5*/ }
		if (x1 < x2 && Entre(y1, y2)) { angulo += 90.0; /*caso 6*/ }
		if (Entre(x1, x2) && y1 < y2) { /*angulo += 0.0;*/ /*caso 7*/ }
		if (x1 > x2 && Entre(y1, y2)) { angulo -= 90.0; /*caso 8*/ }
		//System.out.println("[2] angulo: " + angulo);
		return heading - angulo;
	}
    public static boolean Entre(double v1, double v2){
    	return Entre(v1, v2, 2.0);
    }
	public static boolean Entre(double v1, double v2, double faixa){
		//verifica entre uma faixa de valores, por causa do arredondamento
		double vlr = Math.abs(v1-v2);
		return vlr <= faixa;
	}
    //--------------------------------------------------------
    public static double getAnguloDoisPontos(XY p1, XY p2, double Heading) {
    	return getAnguloDoisPontos(p1.X(), p1.Y(), p2.X(), p2.Y(), Heading);
    }
    
    /**
     * @return o angulo em graus que o robo deve se movimentar (left) para ficar com a fronte correta
     * */
    public static double getAnguloDoisPontos(double x1, double y1, double x2, double y2, double Heading) {
		double radarOffset = Math.toRadians(Heading) - 
							 	absbearing(x1, y1, x2, y2);
		if (radarOffset < 0)
			radarOffset -= Math.PI / 8;
		else
			radarOffset += Math.PI / 8;
		// rodar via left
		return Math.toDegrees(NormaliseBearing(radarOffset));
	}

    public static double NormaliseBearing(double ang) {
		if (ang > Math.PI)
			ang -= 2 * Math.PI;
		if (ang < -Math.PI)
			ang += 2 * Math.PI;
		return ang;
	}

	public double anguloRelativo(double ANG) {
		if (ANG > -180 && ANG <= 180) {
			return ANG;
		}
		double REL = ANG;
		while (REL <= -180) {
			REL += 360;
		}
		while (ANG > 180) {
			REL -= 360;
		}
		return REL;
	}

	/**
	 * @return perto da parede
	 * */
	public static boolean pertoParede(double width, double height, double x, double y) {
		return (x < 50 || x > width - 50 || y < 50 || y > height - 50);
	}
	
	public static double NormaliseHeading(double ang) {
		if (ang > 2 * Math.PI)
			ang -= 2 * Math.PI;
		if (ang < 0)
			ang += 2 * Math.PI;
		return ang;
	}

	 /**
	  * Angulo entre 2 pontos
	  * */
	public static double absbearing(double x1, double y1, double x2, double y2) {
		double xo = x2 - x1;
		double yo = y2 - y1;
		double h = distanciaPontos(x1, y1, x2, y2);
		if (xo > 0 && yo > 0) {
			return Math.asin(xo / h);
		}
		if (xo > 0 && yo < 0) {
			return Math.PI - Math.asin(xo / h);
		}
		if (xo < 0 && yo < 0) {
			return Math.PI + Math.asin(-xo / h);
		}
		if (xo < 0 && yo > 0) {
			return 2.0 * Math.PI - Math.asin(-xo / h);
		}
		return 0;
	}
    
}
