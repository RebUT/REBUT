package utils.maths;

/**
 * Cette classe regroupe les éléments propres au positionnement d'un élément et
 * du robot en particulier.
 * 
 * Le positionnement se base sur une coordonnée dans le plan (x, y) et un angle.
 * La coordonnée correspond au positionnement de l'élément et l'angle à son
 * orientation. Les unités de référence sont les suivantes : - les millimètres
 * pour les distances (x et y), - les radians (PI) pour les angles.
 * 
 * Les références du plan se basent sur les règles suivantes : - Si l'angle
 * associé à l'élément vaut 0 alors l'élément est parallèle à l'axe X. - Si
 * l'angle associé à l'élément vaut PI/2 alors l'élément est parallèle à l'axe
 * Y.
 */

public class Position {

	private double x;

	private double y;

	private double angle;

	public Position(double x, double y, double angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;

		// On reste entre 0 et Pi
		while (this.angle < -Math.PI) {
			this.angle += 2.0 * Math.PI;
		}
		while (this.angle > Math.PI) {
			angle -= 2.0 * Math.PI;
		}
	}

	public Position(Position pos) {
		this.x = pos.x;
		this.y = pos.y;
		this.angle = pos.angle;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

}
