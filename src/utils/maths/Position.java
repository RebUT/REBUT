package utils.maths;

/**
 * Cette classe regroupe les �l�ments propres au positionnement d'un �l�ment et
 * du robot en particulier.
 * 
 * Le positionnement se base sur une coordonn�e dans le plan (x, y) et un angle.
 * La coordonn�e correspond au positionnement de l'�l�ment et l'angle � son
 * orientation. Les unit�s de r�f�rence sont les suivantes : - les millim�tres
 * pour les distances (x et y), - les radians (PI) pour les angles.
 * 
 * Les r�f�rences du plan se basent sur les r�gles suivantes : - Si l'angle
 * associ� � l'�l�ment vaut 0 alors l'�l�ment est parall�le � l'axe X. - Si
 * l'angle associ� � l'�l�ment vaut PI/2 alors l'�l�ment est parall�le � l'axe
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
