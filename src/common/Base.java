package common;

import java.io.IOException;

import utils.configuration.Configuration;
import utils.maths.Position;

import common.Stepper.sensRotation;

public class Base implements IElement {

	/**
	 * Base roulante du robot
	 */

	// Moteur gauche
	private Stepper motorLeft;

	// Moteur droit
	private Stepper motorRight;

	// Position de la base, qui correspond à la position du robot
	private Position positionBase;

	// Position destination
	private Position positionDestination;

	// Booleen qui permet de savoir si on est arrivé
	private boolean arrived;

	private double wheelDiameter;

	public Base() {
		// TODO : constructeur base
		// this.motorLeft = new Stepper(
		this.motorLeft = new Stepper();
		this.motorRight = new Stepper();
		this.setPositionDestination(new Position(0., 0., 0.));
		this.setArrived(false);
		this.setWheelDiameter(0.0);
	}

	@Override
	public void initialize() {
		try {
			motorLeft.initialize(Stepper.idMotor.left);
			motorRight.initialize(Stepper.idMotor.right);
			this.setWheelDiameter(Configuration.getDouble("wheelDiameter"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.setPositionDestination(new Position(0., 0., 0.));
		this.setArrived(false);
	}

	// Getter, setter
	public void setPositionBase(Position positionBase) {
		this.positionBase = positionBase;
	}

	public Position getPositionBase() {
		return positionBase;
	}

	public void setPositionDestination(Position positionDestination) {
		this.positionDestination = positionDestination;
	}

	public Position getPositionDestination() {
		return positionDestination;
	}

	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}

	public boolean isArrived() {
		return arrived;
	}

	public void setWheelDiameter(double wheelDiameter) {
		this.wheelDiameter = wheelDiameter;
	}

	public double getWheelDiameter() {
		return wheelDiameter;
	}

	// Methodes de deplacement de la base
	public int[] moveDistance(double distance) {
		// On avance de la distance donnée

		int[] result = new int[2];

		if (distance < 0) {
			// Si distance negative, on recule
			motorLeft.setSens(sensRotation.BACKWARD);
			motorRight.setSens(sensRotation.BACKWARD);
		} else {
			motorLeft.setSens(sensRotation.FORWARD);
			motorRight.setSens(sensRotation.FORWARD);
		}

		double wheelDiameter = this.getWheelDiameter();
		int nbStep = motorLeft.getNbStep();

		// Perimetre roue
		double perimetre = Math.PI * wheelDiameter;

		// nbPas à faire pour parcourir la distance voulue
		int nbStepToDo = (int) ((distance * nbStep) / perimetre);

		result[0] = nbStepToDo;
		result[1] = nbStepToDo;

		return result;
	}

	// Main de test
	public static void main(String args[]) {
		Base b = new Base();
		b.initialize();
		int result[] = new int[2];

		result = b.moveDistance(100);

		System.out.println(result[0]);
		System.out.println(result[1]);
	}

}
