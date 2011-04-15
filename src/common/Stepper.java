package common;

import java.io.IOException;

import utils.configuration.Configuration;

public class Stepper{

	public enum sensRotation {
		FORWARD, BACKWARD;
	}

	public enum idMotor {
		left, right;
	}

	/**
	 * Variables d'un moteur
	 */

	// Vitesse
	private int vitesseMoteur;

	// Sens de rotation du moteur
	private sensRotation sens;

	// nbStep
	private int nbStep;

	// moteur activé ou pas
	private boolean disabled;

	public Stepper(int vitesseMoteur, sensRotation sens, int nbStep,
			boolean disabled) {
		this.vitesseMoteur = vitesseMoteur;
		this.sens = sens;
		this.nbStep = nbStep;
		this.disabled = disabled;
	}

	public Stepper() {
		this.vitesseMoteur = 0;
		this.sens = sensRotation.FORWARD;
		this.nbStep = 0;
		this.disabled = true;
	}

	public int getVitesseMoteur() {
		return vitesseMoteur;
	}

	public void setVitesseMoteur(int vitesseMoteur) {
		this.vitesseMoteur = vitesseMoteur;
	}

	public sensRotation getSens() {
		return sens;
	}

	public void setSens(sensRotation sens) {
		this.sens = sens;
	}

	public int getNbStep() {
		return nbStep;
	}

	public void setNbStep(int nbStep) {
		this.nbStep = nbStep;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	// Permet d'initialiser un moteur à partir d'un fichier de configuration
	public void initialize(idMotor idMotor) throws IOException {
		// TODO : methode d'initialisaiton d'un moteur à partir d'un fichier de
		// configuration

		if (idMotor == Stepper.idMotor.left) {
			this.vitesseMoteur = Configuration.getInt("motorLeftSpeed");
		} else {
			this.vitesseMoteur = Configuration.getInt("motorRightSpeed");
		}

		this.nbStep = Configuration.getInt("nbStep");
	}

	// Reinitialisation moteur
	public void resetStepper() {
		// TODO : remise à zero d'un moteur
	}
}
