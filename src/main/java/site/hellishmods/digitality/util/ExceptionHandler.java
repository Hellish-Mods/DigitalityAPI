package site.hellishmods.digitality.util;

import site.hellishmods.digitality.digitality;

// Logs exceptions
public class ExceptionHandler {
    public ExceptionHandler(Exception e) {
        switch (e.getClass().getCanonicalName()) {
            case "java.io.IOException": // IOException
                digitality.LOGGER.error("File not existing resulted in an IOException, whoopsy!");
                break;
        }
    }
}
