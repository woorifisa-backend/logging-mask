package dev.logging;

import dev.logging.app.LogSimulation;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		LogSimulation app = new LogSimulation();
        app.start();
	}
}