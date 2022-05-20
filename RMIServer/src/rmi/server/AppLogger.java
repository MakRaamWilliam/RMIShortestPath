package rmi.server;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppLogger {
	private FileHandler fh;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public AppLogger() {
		configureLogger();
	}

	public void logInfo(String message) {
		LOGGER.log(Level.INFO, message);
	}

	private void configureLogger() {
		try {
			// This block configure the logger with handler and formatter
			fh = new FileHandler("MyLogFile.log");
			LOGGER.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
