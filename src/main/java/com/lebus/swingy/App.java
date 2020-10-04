package com.lebus.swingy;

import com.lebus.swingy.controller.Controller;
import com.lebus.swingy.model.PlayerCharacter;
import com.lebus.swingy.model.PlayerClasses;
import com.lebus.swingy.util.ValidateInput;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;

/**
 *
 * @author yaqwaqwa
 */
public class App {

    public static volatile boolean GUI;
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    public static final Validator validator = factory.getValidator();

    public static void usage() {
        System.err.println("\n\tWRONG USAGE");
        System.err.println("\tjava <PROGRAM NAME> <CONSOLE | GUI>\n\tOR\n\tjava -jar <.JAR FILE> <CONSOLE | GUI>");
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            if (args[0].toLowerCase().equals("gui")) {
                GUI = true;
            }
            else if (args[0].toLowerCase().equals("console")) {
                GUI = false;
            }
            else
                usage();
            start();
        } else
            usage();

    }

    public static void start() {
        Controller controller = new Controller();
        controller.toStartPage();
    }

}
