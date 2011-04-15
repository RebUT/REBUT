package utils.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//@SuppressWarnings("static-access")
public class Configuration {
	/**
	 * Classe permettant de gérer un fichier de configuration
	 */

	static String configurationFile = "/Users/gallaz/Documents/workspace/CodePerso/resource/conf.properties";

	public Configuration(String confFile) {
		Configuration.configurationFile = confFile;
	}

	public static String getConfigurationFile() {
		return configurationFile;
	}

	public static double getDouble(String property) throws IOException {
		// On cherche dans le fichier de configuration, on cherche la propriete
		// qui va bien
		FileInputStream f = new FileInputStream(getConfigurationFile());
		Properties p = new Properties();
		p.load(f);

		if (p.getProperty(property) != null) {
			return Double.parseDouble(p.getProperty(property));
		} else {
			return 0.0;
		}
	}

	public static int getInt(String property) throws IOException {
		// On cherche dans le fichier de configuration, on cherche la propriete
		// qui va bien
		FileInputStream f = new FileInputStream(getConfigurationFile());
		Properties p = new Properties();
		p.load(f);

		if (p.getProperty(property) != null) {
			return Integer.parseInt(p.getProperty(property));
		} else {
			return 0;
		}
	}

	public static String getString(String property) throws IOException {
		// On cherche dans le fichier de configuration, on cherche la propriete
		// qui va bien
		FileInputStream f = new FileInputStream(getConfigurationFile());
		Properties p = new Properties();
		p.load(f);

		if (p.getProperty(property) != null) {
			return p.getProperty(property);
		} else {
			return "";
		}
	}

	// Main de test
	// public static void main(String[] args) throws IOException {
	// Configuration confEssai = new Configuration(
	// "/Users/gallaz/Documents/workspace/CodePerso/resource/conf.properties");
	//
	// double essai = confEssai.getDouble("moteurLeftSpeed");
	//
	// System.out.println(essai);
	// }
}
