package numbers;

import java.util.Arrays;

enum NumberProperty {
    BUZZ("buzz"), DUCK("duck"), PALINDROMIC("palindromic"), GAPFUL("gapful"), SPY("spy"),
    SUNNY("sunny"), SQUARE("square"), JUMPING("jumping"), HAPPY("happy"), SAD("sad"), EVEN("even"), ODD("odd");

    NumberProperty(String name) {
        this.name = name;
    }

    private String name;
    private static String[][] exclusivesProperties;

    public static boolean isValidProperty(String prop) {
        try {
            if (prop.startsWith("-")) {
                valueOf(prop.substring(1).toUpperCase());
            } else {
                valueOf(prop.toUpperCase());
            }
            return true;
        } catch (IllegalArgumentException ignored) {
        }
        return false;
    }

    public boolean is(String prop){
        return name.equalsIgnoreCase(prop);
    }

    public static String[] getInvalidProps(String[] props) {
        StringBuilder sb = new StringBuilder();
        for (String prop : props) {
            if (!isValidProperty(prop)) {
                if (sb.toString().isEmpty()) {
                    sb.append(prop);
                } else {
                    sb.append(", ").append(prop);
                }
            }
        }
        if (sb.length() > 0) {
            return sb.toString().split(", ");
        }
        return new String[0];
    }

    private static String[][] subExclusiveProperties() {
        return new String[][]{{EVEN.name, ODD.name}, {DUCK.name, SPY.name}, {SQUARE.name, SUNNY.name}, {HAPPY.name, SAD.name}};
    }

    public static String[] getExclusiveProperties(String[] props) {
        if (props.length == 1) {
            return new String[0];
        }

        StringBuilder sb;
        if (exclusivesProperties == null) {
            exclusivesProperties = new String[values().length + subExclusiveProperties().length * 2 - 1][2];
            int i = 0;
            for (NumberProperty value : values()) {
                exclusivesProperties[i++] = new String[]{value.toString().toLowerCase(), "-" + value.toString().toLowerCase()};
            }

            for (String[] items : subExclusiveProperties()) {
                exclusivesProperties[i++] = new String[]{items[0].toLowerCase(), items[1].toLowerCase()};
                if(items[0].equalsIgnoreCase(SQUARE.name)){
                    continue;
                }
                exclusivesProperties[i++] = new String[]{"-" + items[0].toLowerCase(), "-" + items[1].toLowerCase()};
            }
        }

        int n;
        for (String[] exclusiveProperties : exclusivesProperties) {
            n = 0;
            sb = new StringBuilder();
            for (String exclusiveProperty : exclusiveProperties) {
                for (String prop : props) {
                    if (prop.equalsIgnoreCase(exclusiveProperty)) {
                        n++;
                        if (sb.length() == 0) {
                            sb.append(exclusiveProperty);
                        } else {
                            sb.append(", ").append(exclusiveProperty);
                        }
                    }
                }
            }
            if (n == exclusiveProperties.length) {
                return sb.toString().split(", ");
            }
        }
        return new String[0];
    }

}
