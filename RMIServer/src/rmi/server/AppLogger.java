package rmi.server;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppLogger {
	private FileHandler fileHandler;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public AppLogger() {
		configure();
	}

	public void logInfo(String message) {
		LOGGER.log(Level.INFO, message);
	}

	private void configure() {
		try {
			fileHandler = new FileHandler("Log.log");
			LOGGER.addHandler(fileHandler);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
}
