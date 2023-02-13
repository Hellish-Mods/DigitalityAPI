package site.hellishmods.digitality.util;

import site.hellishmods.digitality.digitality;

// Logs exceptions
public class ExceptionHandler {
    public ExceptionHandler(Exception e) {
        switch (e.getClass().getCanonicalName()) {
            case "java.io.IOException": // IOException
                digitality.LOGGER.error("An IOException occurred. Possibly a bug on our end. Report this to our github!");
                break;
        }
    }
}
