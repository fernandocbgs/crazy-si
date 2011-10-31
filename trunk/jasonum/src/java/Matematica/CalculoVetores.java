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
    
    public static double AnguloTeste(XY p1, XY p2){
    	XY v1 = getVetor(p1, p2);
    	XY v2 = getVetor(
    				new XY(p1.X(),p2.Y())
    				,
    				new XY(p1.X(),p1.Y())
    			); //vetor criado, base de calculo do angulo
    	
    	System.out.println("v1: " + v1 + ", v2: " + v2);
    	
    	//o = arccos( u * v / |v|*|u| )
    	double o = Math.acos(multiplicao(v1, v2) / (norma(v1) * norma(v2)));
    	
    	System.out.println("q1: " + o);
    	o = Math.toDegrees(o); //(float)
    	System.out.println("q2: " + o);

    	return o;
    }
    
    public static double AnguloPontos(XY p11, XY p12, XY p21, XY p22){
        return AnguloVetores(getVetor(p11, p12), getVetor(p21, p22));
    }
    
    public static double AnguloVetores(XY v1, XY v2){
        double calc = (multiplicao(v1, v2) / (norma(v1)+ norma(v2)));
        return Math.acos(calc); //arcocosseno
    }
}
