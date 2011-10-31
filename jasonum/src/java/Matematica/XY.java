package Matematica;

public class XY {
    private double _x, _y;
    public void X(double x){_x=x;}
    public void Y(double y){_y=y;}
    public double X(){return _x;}
    public double Y(){return _y;}
    public XY(){ X(0); Y(0); }
    public XY(double x, double y){ X(x); Y(y); }
    public String toString(){ return "{"+ X() + "," + Y() + "}"; }
}
