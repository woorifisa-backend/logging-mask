package dev.logging;

import dev.logging.app.LogSimulation;

public class Main {

	public static void main(String[] args) {
		LogSimulation app = new LogSimulation();
        app.start();
	}

        System.out.println("=== 프로그램 종료 ===");
    }
}
