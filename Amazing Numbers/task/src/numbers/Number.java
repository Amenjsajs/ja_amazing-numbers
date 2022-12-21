package numbers;

import java.util.Arrays;

public class Number {
    public static final String FIRST_PARAM_INVALID = "The first parameter should be a natural number or zero.";
    public static final String SECOND_PARAM_INVALID = "The second parameter should be a natural number.";

    private String request;
    private long number = 0;
    private int listSize = 0;
    private String[] props;

    public Number(String request) {
        this.request = request;
        init(request);
    }

    private void init(String request) {
        String[] split = request.split(" ");
        try {
            number = Long.parseLong(split[0]);
        } catch (NumberFormatException ignored) {
        }

        this.props = new String[0];
        if (split.length >= 3) {
            int len = split.length;
            StringBuilder sb = new StringBuilder("0" + split[2]);
            for (int i = 3; i < len; i++) {
                if (!sb.toString().contains("0" + split[i].toLowerCase())) {
                    sb.append(", ").append("0").append(split[i].toLowerCase());
                }
            }

            this.props = sb.toString().replaceAll("0","").split(", ");
        }

        if (split.length >= 2) {
            try {
                listSize = Integer.parseInt(split[1]);
            } catch (NumberFormatException ignored) {
            }
        }
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public boolean isNatural() {
        return isNatural(number);
    }

    private boolean isNatural(long i) {
        return i > 0;
    }

    public boolean isEven() {
        return isEven(number);
    }

    public static boolean isEven(long number) {
        return number % 2 == 0;
    }

    public boolean isOdd() {
        return !isEven();
    }

    public static boolean isOdd(long number) {
        return !isEven(number);
    }

    public boolean isBuzzNumber() {
        return isBuzzNumber(number);
    }

    private boolean isBuzzNumber(long number) {
        return isNatural(number) && number % 7 == 0 || number % 10 == 7;
    }

    public boolean isDuckNumber() {
        return isDuckNumber(number);
    }

    private boolean isDuckNumber(long number) {
        return isNatural() && (number + "").contains("0");
    }

    public boolean isPalindromic() {
        return isPalindromic(number);
    }

    private boolean isPalindromic(long number) {
        String str = number + "";

        for (int i = 0, len = str.length(); i < len / 2; i++) {
            if (str.charAt(i) != str.charAt((len - 1) - i)) {
                return false;
            }
        }

        return true;
    }

    public boolean isGapfulNumber() {
        return isGapfulNumber(number);
    }

    public static boolean isGapfulNumber(long number) {
        if (number < 100) {
            return false;
        }
        String str = number + "";
        int n = Integer.parseInt(str.charAt(0) + "" + str.charAt(str.length() - 1));
        return number % n == 0;
    }

    public boolean isSpyNumber() {
        return isSpyNumber(number);
    }

    public static boolean isSpyNumber(long number) {
        char[] digits = (number + "").toCharArray();
        long sum = 0;
        long mult = 1;

        for (char digit : digits) {
            sum += Long.parseLong(digit + "");
            mult *= Long.parseLong(digit + "");
        }
        return sum == mult;
    }

    public boolean isSquareNumber() {
        return isSquareNumber(number);
    }

    public static boolean isSquareNumber(long number) {
        double sqrt = Math.sqrt(number);
        return (sqrt - Math.floor(sqrt)) == 0;
    }

    public boolean isSunnyNumber() {
        return isSunnyNumber(number);
    }

    public static boolean isSunnyNumber(long number) {
        return isSquareNumber(number + 1);
    }

    public boolean isJumpingNumber() {
        return isJumpingNumber(number);
    }

    public static boolean isJumpingNumber(long number) {
        if (number < 10) {
            return true;
        }

        char[] digits = (number + "").toCharArray();
        int n1;
        int n2;
        for (int i = 1, len = digits.length; i < len; i++) {
            n1 = Integer.parseInt(digits[i - 1] + "");
            n2 = Integer.parseInt(digits[i] + "");
            if (Math.abs(n1 - n2) != 1) {
                return false;
            }
        }
        return true;
    }

    public boolean isHappyNumber() {
        return isHappyNumber(number);
    }

    public static boolean isHappyNumber(long number) {
        long n = number;
        char[] digits;
        long sum;
        do {
            digits = (n + "").toCharArray();
            sum = 0;
            for (char digit : digits) {
                sum += (int) Math.pow(Integer.parseInt(digit + ""), 2);
            }
            if (sum == 1) {
                return true;
            } else if (sum == number) {
                return false;
            } else if (sum < 10) {
                return false;
            }
            n = sum;
        } while (true);
    }

    public boolean isSadNumber() {
        return isSadNumber(number);
    }

    public static boolean isSadNumber(long number) {
        return !isHappyNumber(number);
    }

    private static String singlePropertyState(NumberProperty prop, boolean state) {
        return String.format("%12s: %b\n", prop.name().toLowerCase(), state);
    }

    private String getProperties(long number) {
        if (!isNatural()) {
            return FIRST_PARAM_INVALID;
        }

        StringBuilder properties = new StringBuilder();
        properties
                .append(singlePropertyState(NumberProperty.BUZZ, isBuzzNumber()))
                .append(singlePropertyState(NumberProperty.DUCK, isDuckNumber()))
                .append(singlePropertyState(NumberProperty.PALINDROMIC, isPalindromic()))
                .append(singlePropertyState(NumberProperty.GAPFUL, isGapfulNumber()))
                .append(singlePropertyState(NumberProperty.SPY, isSpyNumber()))
                .append(singlePropertyState(NumberProperty.SQUARE, isSquareNumber()))
                .append(singlePropertyState(NumberProperty.SUNNY, isSunnyNumber()))
                .append(singlePropertyState(NumberProperty.JUMPING, isJumpingNumber()))
                .append(singlePropertyState(NumberProperty.HAPPY, isHappyNumber()))
                .append(singlePropertyState(NumberProperty.SAD, isSadNumber()))
                .append(singlePropertyState(NumberProperty.EVEN, isEven()))
                .append(singlePropertyState(NumberProperty.ODD, isOdd()));

        return String.format("Properties of %d\n%s", number, properties);
    }

    private String getPropertyState(String properties, long number, String type) {
        return properties.isEmpty() ? String.format("%d is %s", number, type) : String.format("%s, %s", properties, type);
    }

    private boolean isRejectedProp(NumberProperty property) {
        for (String prop : props) {
            if (prop.startsWith("-")) {
                if (property.is(prop.substring(1))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isRequiredProp(NumberProperty property) {
        for (String prop : props) {
            if (property.is(prop)) {
                return true;
            }
        }
        return false;
    }

    private String getPropertiesInLine(long number) {
        String properties = "";

        if (isBuzzNumber(number)) {
            if (isRejectedProp(NumberProperty.BUZZ)) {
                return "";
            }
            properties = getPropertyState(properties, number, NumberProperty.BUZZ.name().toLowerCase());
        } else if (isRequiredProp(NumberProperty.BUZZ)) {
            return "";
        }

        if (isDuckNumber(number)) {
            if (isRejectedProp(NumberProperty.DUCK)) {
                return "";
            }
            properties = getPropertyState(properties, number, NumberProperty.DUCK.name().toLowerCase());
        } else if (isRequiredProp(NumberProperty.DUCK)) {
            return "";
        }

        if (isPalindromic(number)) {
            if (isRejectedProp(NumberProperty.PALINDROMIC)) {
                return "";
            }
            properties = getPropertyState(properties, number, NumberProperty.PALINDROMIC.name().toLowerCase());
        } else if (isRequiredProp(NumberProperty.PALINDROMIC)) {
            return "";
        }

        if (isGapfulNumber(number)) {
            if (isRejectedProp(NumberProperty.GAPFUL)) {
                return "";
            }
            properties = getPropertyState(properties, number, NumberProperty.GAPFUL.name().toLowerCase());
        } else if (isRequiredProp(NumberProperty.GAPFUL)) {
            return "";
        }

        if (isSpyNumber(number)) {
            if (isRejectedProp(NumberProperty.SPY)) {
                return "";
            }
            properties = getPropertyState(properties, number, NumberProperty.SPY.name().toLowerCase());
        } else if (isRequiredProp(NumberProperty.SPY)) {
            return "";
        }

        if (isSquareNumber(number)) {
            if (isRejectedProp(NumberProperty.SQUARE)) {
                return "";
            }
            properties = getPropertyState(properties, number, NumberProperty.SQUARE.name().toLowerCase());
        } else if (isRequiredProp(NumberProperty.SQUARE)) {
            return "";
        }

        if (isSunnyNumber(number)) {
            if (isRejectedProp(NumberProperty.SUNNY)) {
                return "";
            }
            properties = getPropertyState(properties, number, NumberProperty.SUNNY.name().toLowerCase());
        } else if (isRequiredProp(NumberProperty.SUNNY)) {
            return "";
        }

        if (isJumpingNumber(number)) {
            if (isRejectedProp(NumberProperty.JUMPING)) {
                return "";
            }
            properties = getPropertyState(properties, number, NumberProperty.JUMPING.name().toLowerCase());
        } else if (isRequiredProp(NumberProperty.JUMPING)) {
            return "";
        }

        if (isHappyNumber(number)) {
            if (isRejectedProp(NumberProperty.HAPPY)) {
                return "";
            }
            properties = getPropertyState(properties, number, NumberProperty.HAPPY.name().toLowerCase());
        } else if (isRequiredProp(NumberProperty.HAPPY)) {
            return "";
        }

        if (isSadNumber(number)) {
            if (isRejectedProp(NumberProperty.SAD)) {
                return "";
            }
            properties = getPropertyState(properties, number, NumberProperty.SAD.name().toLowerCase());
        } else if (isRequiredProp(NumberProperty.SAD)) {
            return "";
        }

        if (isEven(number)) {
            if (isRejectedProp(NumberProperty.EVEN)) {
                return "";
            }
            properties = getPropertyState(properties, number, NumberProperty.EVEN.name().toLowerCase());
        } else if (isRequiredProp(NumberProperty.EVEN)) {
            return "";
        }

        if (isOdd(number)) {
            if (isRejectedProp(NumberProperty.ODD)) {
                return "";
            }
            properties = getPropertyState(properties, number, NumberProperty.ODD.name().toLowerCase());
        } else if (isRequiredProp(NumberProperty.ODD)) {
            return "";
        }

        return properties;
    }

    public String getProperties() {
        if (!request.contains(" ")) {
            return getProperties(number);
        }

        if (!isNatural()) {
            return FIRST_PARAM_INVALID;
        }

        if (!isNatural(listSize)) {
            return SECOND_PARAM_INVALID;
        }

        StringBuilder properties = new StringBuilder();

        if (props.length > 0) {
            String[] invalidProperties = NumberProperty.getInvalidProps(props);
            if (invalidProperties.length == 1) {
                return String.format("The property %s is wrong.\nAvailable properties: %s", Arrays.toString(invalidProperties).toUpperCase(), Arrays.toString(NumberProperty.values()).toUpperCase());
            }

            if (invalidProperties.length >= 2) {
                return String.format("The properties %s are wrong.\nAvailable properties: %s", Arrays.toString(invalidProperties).toUpperCase(), Arrays.toString(NumberProperty.values()).toUpperCase());
            }

            String[] exclusiveProperties = NumberProperty.getExclusiveProperties(this.props);
            if (exclusiveProperties.length > 0) {
                return String.format("The request contains mutually exclusive properties: %s\nThere are no numbers with these properties.", Arrays.toString(exclusiveProperties).toUpperCase());
            }

            String line;
            long n = number;

            do {
                line = getPropertiesInLine(n++);

                if (!line.isEmpty()) {
                    listSize--;
                    properties.append(line).append("\n");
                }
            } while (listSize > 0);
            return properties.toString();
        }

        for (long i = number, last = number + listSize; i < last; i++) {
            properties.append(getPropertiesInLine(i)).append("\n");
        }
        return properties.toString();
    }
}