package me.borawski.perseus.commands;

import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author ethan
 */
public class CommandListener {

    /*
     * Methods
     */

    public CommandListener() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String[] command = scanner.nextLine().toLowerCase().split(" ");
                    try {
                        CommandHandler.runCommand(command);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
}
