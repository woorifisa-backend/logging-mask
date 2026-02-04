package dev.logging;

import dev.logging.app.LogSimulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	public static void main(String[] args) {
		LogSimulation app = new LogSimulation();
        app.start();
	}
}