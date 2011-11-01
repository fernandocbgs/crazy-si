package DadosRobos;

import java.util.List;

/**
 * Representa um Robo
 * */
public class DadosRobos {
	private int _indiceRobo;
	private String _nomeRobo;
	private double _energia;
	private double _x;
	private double _y;
	private double _velocidade;
	private double _heading;
	private double _width;
	private double _height;
	private int _numeroRound;

	public void setIndiceRobo(int indiceRobo) { this._indiceRobo = indiceRobo; }
	public int getIndiceRobo() { return _indiceRobo; }
	public void setNomeRobo(String nomeRobo) { this._nomeRobo = nomeRobo; }
	public String getNomeRobo() { return _nomeRobo; }
	public void setEnergia(double energia) { this._energia = energia; }
	public double getEnergia() { return _energia; }
	public void setX(double x) { this._x = x; }
	public double getX() { return _x; }
	public void setY(double y) { this._y = y; }
	public double getY() { return _y; }
	public void setVelocidade(double velocidade) { this._velocidade = velocidade; }
	public double getVelocidade() { return _velocidade; }
	public void setHeading(double heading) { this._heading = heading; }
	public double getHeading() { return _heading; }
	public void setWidth(double width) { this._width = width; }
	public double getWidth() { return _width; }
	public void setHeight(double height) { this._height = height; }
	public double getHeight() { return _height; }
	public void setNumeroRound(int _numeroRound) { this._numeroRound = _numeroRound; }
	public int getNumeroRound() { return _numeroRound; }
	
	public DadosRobos(){}
	public DadosRobos(List<String> l) {
		setIndiceRobo(Integer.valueOf(l.get(0)));
		setNomeRobo(l.get(1));
		setEnergia(Double.parseDouble(l.get(2)));
		setX(Double.parseDouble(l.get(3)));
		setY(Double.parseDouble(l.get(4)));
		setVelocidade(Double.parseDouble(l.get(5)));
		setHeading(Double.parseDouble(l.get(6)));
		setWidth(Double.parseDouble(l.get(7)));
		setHeight(Double.parseDouble(l.get(8)));
		setNumeroRound(Integer.valueOf(l.get(9)));
	}
	
	public DadosRobos getDadosRobos(List<String> l) {
		return new DadosRobos(l);
	}
	
	@Override
	public String toString() {
		return _indiceRobo + "-"+_nomeRobo+"-"+_energia+"-["+_x + "," + _y +"]" + "-" +
			   _velocidade+"-"+_heading+"-"+_width+"-"+_height + "-" + _numeroRound;
	}
	
	
}