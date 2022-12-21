class EnglishAlphabet {

    public static StringBuilder createEnglishAlphabet() {
        StringBuilder sb = new StringBuilder("A");
        for (char i = 'B'; i <= 'Z'; i++){
            sb.append(" ").append(i);
        }

        return sb;
    }
}