package sample;

import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

public class Definitivo extends AdvancedRobot {
	double movimiento; // cuanto nos movemos
	double movMaxHor; // movimiento maximo horizontal
	double movMaxVer; // movimiento maximo vertical
	double potencia; // potencia de disparo
	// variables de estado para saber el movimiento siguiendo las paredes
	int pared = 2;
	// variable final para tener el valor de 180º
	final double PI = Math.PI;
	Enemigo target;// referencia a un objeto de la clase enemigo
	// varibles que nos ayudan a saber en que sentido va el escaner y el robot
	int direction = 1;
	int sentidoEscaner = 1;

	/**
	 * run: Método principal de nuestro Robot
	 */
	public void run() {
		// Coloreamos nuestro robot
		setColors(Color.black, Color.black, Color.yellow);
		// inicializamos variables del entorno de combate y nos creamos el
		// enemigo
		target = new Enemigo();
		target.distance = 100000;
		movMaxHor = getBattleFieldWidth();
		movMaxVer = getBattleFieldHeight();
		// Iniciamos movimiento hacia la pared superior y giramos para empezar
		// el movimiento evasivo
		turnLeft(getHeading());
		movimiento = movMaxVer - getY() - 25;
		ahead(movimiento);
		turnRight(90);// activamos las funciones del cañon y radar para que sean
						// independientes
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		// Bucle principal de funcionamiento
		while (true) {
			// nos movemos y actualizamos la pared en la que estamos
			pared = mover();
			// escaneamos en busca del objetivo, y si cumple las
			// especificaciones de
			// cercania disparamos
			escanear();
			calcularTiro();
			if (calcularPotencia())
				fire(potencia); // Disparamos
			execute();
		}
	}

	/**
	 * mover: Metodo que define el movimiento general de nuestro Robot
	 */
	public int mover() {
		// varible que contiene el movimiento maximo que puede realizar
		// el robot
		double movimientoMax = 100000;
		// variable local que nos indica en que pared estamos
		int isInWall;
		isInWall = pared;
		// el movimiento esta comprendido entre 300 y 0, siendo mas o
		// menos aleatorio
		movimiento = Math.random() * 300;
		// si nos ha salido un movimiento muy pequeño, lo cambiamos
		if (movimiento < 50)
			movimiento = 50;
		switch (isInWall) {
		// actualizamos el movimiento maximo en funcion de la pared en
		// la que
		// estemos y la situacion del robot actual
		case 1:
			movimientoMax = movMaxVer - getY() - 25;
			break;
		case 2:
			movimientoMax = movMaxHor - getX() - 25;
			break;
		case 3:
			movimientoMax = getY() - 25;
			break;
		case 4:
			movimientoMax = getX() - 25;
			break;
		default:
			movimientoMax = 25;
			pared = 4;
		}
		// si el movimiento es justo para llegar al fin de esa pared,
		// giramos, sino simplemente nos movemos hacia adelante
		if (movimiento > movimientoMax
				&& (pared == 1 || pared == 2 || pared == 3 || pared == 4)) {
			if (pared == 4 && movimientoMax == 25) {
				isInWall = 4;
			} else {
				isInWall = ++pared % 4;
				movimiento = movimientoMax;
				ahead(direction * movimiento);
				turnRight(90);
			}
		} else {
			setAhead(movimiento);
		}
		return isInWall;
	}

	/**
	 * onHitRobot: Si chocamos con un adversario, retrocedemos o avanzamos segun
	 * donde este y cambiamos el sentido de la marcha.
	 */
	public void onHitRobot(HitRobotEvent e) {
		direction = direction * -1;
		// Si esta delante nuestra, echamos para atrás
		if (e.getBearing() > -90 && e.getBearing() < 90)
			setBack(100);
		// Si está detrás nos desplazamos en sentido contrario
		else
			setAhead(100);
	}

	/**
	 * calcularPotencia(): Calculamos la potencia optima basado en la distancia
	 * al objetivo
	 */
	boolean calcularPotencia() {
		/*
		 * variables auxiliares donde se almacenara la distancia al enemigo y
		 * los valores maximos y minimos que acotan cada potencia de disparo
		 */
		double distancia, min, max;
		distancia = target.distance;
		/*
		 * si la distancia esta fuera del rango asignado, no se dispara, con lo
		 * que se pone el poder de disparo a 0 y se devuelve false
		 */
		if (distancia > 300 || distancia < 0)
			potencia = 0;
		else {
			min = 250;
			max = 300;
			potencia = 3;
			// calculamos la potencia de tiro en funcion de la distancia a la
			// que
			// este
			while (!(distancia > min && distancia < max)) {
				potencia = potencia - 0.5;
				min = min - 50;
				max = max - 50;
				if (distancia > 300 || distancia < 0) {
					potencia = 0;
					break;
				}
			}
			// si esta en el rango, devolvemos true, con lo que se disparara
			if (potencia != 0)
				return true;
		}
		// en caso de no devolver true, es porque no esta en el rango, con lo
		// que
		// no se disparara.
		return false;
	}

	/**
	 * escanear(): Realizamos un scanner
	 */
	void escanear() {
		double radarOffset;
		// Si hace tiempo que no localizamos a nadie
		if (getTime() - target.ctime > 4) {
			radarOffset = 360;// lo rotamos 360º
		} else {
			// Calculamos el giro necesario del radar para seguir al objetivo
			radarOffset = getRadarHeadingRadians()
					- absbearing(getX(), getY(), target.x, target.y);
			// Calculamos el offset debido al seguimiento del objetivo
			// para no perderlo
			if (radarOffset < 0)
				radarOffset -= PI / 8;
			else
				radarOffset += PI / 8;
		}
		// giramos el radar
		setTurnRadarLeftRadians(NormaliseBearing(radarOffset));
	}

	/**
	 * calcularTiro(): Realizamos los cálculos de balística
	 */
	void calcularTiro() {
		// Primera estimación: calculamos el tiempo que tardaría en llegar el
		// proyectil a la posicion actual del objetivo
		long time = getTime() + (int) (target.distance / (20 - (3 * potencia)));
		// calculamos el offset de giro según una estimación de movimiento
		// lineal
		double gunOffset = getGunHeadingRadians()
				- absbearing(getX(), getY(), target.guessX(time),
						target.guessY(time));
		// giramos el cañon
		setTurnGunLeftRadians(NormaliseBearing(gunOffset));
	}

	// ajustamos el ángulo si no se encuentra de -pi a pi
	double NormaliseBearing(double ang) {
		if (ang > PI)
			ang -= 2 * PI;
		if (ang < -PI)
			ang += 2 * PI;
		return ang;
	}

	// Si no se encuentra de 0 a 2pi lo modificamos para que sea el ángulo más
	// corto
	double NormaliseHeading(double ang) {
		if (ang > 2 * PI)
			ang -= 2 * PI;
		if (ang < 0)
			ang += 2 * PI;
		return ang;
	}

	// Calcula la distancia entre dos puntos x e y
	public double getrange(double x1, double y1, double x2, double y2) {
		double xo = x2 - x1;
		double yo = y2 - y1;
		double h = Math.sqrt(xo * xo + yo * yo);
		return h;
	}

	// Calcula el ángulo entre dos puntos
	public double absbearing(double x1, double y1, double x2, double y2) {
		double xo = x2 - x1;
		double yo = y2 - y1;
		double h = getrange(x1, y1, x2, y2);
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

	/**
	 * onScannedRobot: Método que se ejecuta cuando el scanner encuentra un
	 * robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Si encontramos un robot más cercano al objetivo actual
		if ((e.getDistance() < target.distance) || (target.name == e.getName())) {
			// Calcular apuntamiento
			double absbearing_rad = (getHeadingRadians() + e
					.getBearingRadians()) % (2 * PI);
			// actualizamos las variables del nuevo objetivo
			target.name = e.getName();
			target.x = getX() + Math.sin(absbearing_rad) * e.getDistance();
			target.y = getY() + Math.cos(absbearing_rad) * e.getDistance();
			target.bearing = e.getBearingRadians();
			target.head = e.getHeadingRadians();
			target.ctime = getTime();
			target.speed = e.getVelocity();
			target.distance = e.getDistance();
		}
	}

	/**
	 * onRobotDeath: Método que corre cuando muere un adversario
	 */
	public void onRobotDeath(RobotDeathEvent e) {
		if (e.getName() == target.name)
			target.distance = 10000; // actualizamos la variable de distancia
	}

}

/*
 * Clase Enemigo. La utilizamos para almacenar la información acerca de nuestros
 * adversarios Posición, velocidad, disparos, etc
 */
class Enemigo {
	String name;
	public double bearing;
	public double head;
	public long ctime;
	public double speed;
	public double x, y;
	public double distance;

	// metodo que intenta adivinar la coordenada X del enemigo en function de
	// los datos que tenemos de el
	public double guessX(long when) {
		long diff = when - ctime;
		return x + Math.sin(head) * speed * diff;
	}

	// metodo que intenta adivinar la coordenada Y del enemigo en function de
	// los datos que tenemos de el
	public double guessY(long when) {
		long diff = when - ctime;
		return y + Math.cos(head) * speed * diff;
	}
}