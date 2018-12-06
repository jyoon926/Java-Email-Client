
public enum option {
    ;

    private option() {}

    public String value() {
        return name();
    }

    public static option fromValue(String v) {
        return valueOf(v);
    }
}
