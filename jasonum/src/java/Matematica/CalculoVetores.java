package Matematica;

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
    
    @SuppressWarnings("unused")
	@Deprecated
    private static double AnguloTeste(XY p1, XY p2){
		XY v1 = CalculoVetores.getVetor(p1, p2);
		XY v2 = CalculoVetores.getVetor(new XY(p1.X(), p1.Y()), new XY(p2.X(),
				p1.Y())); // vetor criado, base de calculo do angulo

		// System.out.println("v1: " + v1 + ", v2: " + v2);

		// o = arccos( u * v / |v|*|u| )
		double o = Math.acos(CalculoVetores.multiplicao(v1, v2)
				/ (CalculoVetores.norma(v1) * CalculoVetores.norma(v2)));
		o = Math.toDegrees(o);
		System.out.println("o: " + o);
    	return o;
    }
    
    public static double AnguloPontos(XY p11, XY p12, XY p21, XY p22){
        return AnguloVetores(getVetor(p11, p12), getVetor(p21, p22));
    }
    
    public static double AnguloVetores(XY v1, XY v2){
        double calc = (multiplicao(v1, v2) / (norma(v1)+ norma(v2)));
        return Math.acos(calc); //arcocosseno
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
