package site.hellishmods.blackmirrorjs.config;

public enum PermissionLevel {
    GREEN(0x55FF55, "!"),
    YELLOW(0xFFFF55, "!"),
    RED(0xFF5555, "!");

    public final Integer color;
    public final String suffix;

    PermissionLevel(Integer color, String suffix) {
        this.color = color;
        this.suffix = suffix;
    }
}
