package common;

import com.sun.tools.javac.util.Position;

public class Robot {
	/**
	 * Variables du robot
	 */

	// On retrouve la base roulante
	Base baseRoulante;

	// Position du robot (� stocker dans la base ? )
	Position positionActuelle;
	Position positionDestination;

	// Booleen qui nous permet de savoir si on est arriv� � la position de
	// destination
	boolean arrived;

}
