package controller;

import org.jetbrains.annotations.NotNull;

public record Option(char shortName, String longName, OptionType type) {
    public Option {
        if (shortName == 0 && longName == null) {
            throw new IllegalArgumentException("Option must have a short name or a long name");
        }
    }
    public boolean match(@NotNull String arg) {
        if (arg.length() < 2) {
            return false;
        }
        if (arg.length() == 2) {
            return arg.charAt(0) == '-' && arg.charAt(1) == shortName;
        }
        return arg.startsWith("--") && arg.substring(2).equals(longName);
    }
    public Object parse(@NotNull String arg) {
        return switch (type) {
            case STRING -> arg;
            case INT -> Integer.parseInt(arg);
            case LONG -> Long.parseLong(arg);
        };
    }
}
