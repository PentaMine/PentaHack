package net.minecraft.pentahack.util.render;

public enum TextColor {
    DARK_RED("\u00A74"),
    RED("\u00A7c"),
    GOLD("\u00A76"),
    YELLOW("\u00A7e"),
    DARK_GREEN("\u00A72"),
    GREEN("\u00A7a"),
    AQUA("\u00A7b"),
    DARK_AQUA("\u00A73"),
    DARK_BLUE("\u00A71"),
    BLUE("\u00A79"),
    LIGHT_PURPLE("\u00A7d"),
    DARK_PURPLE("\u00A75"),
    WHITE("\u00A7f"),
    GRAY("\u00A77"),
    DARK_GREY("\u00A78"),
    BLACK("\u00A70");

    public String code;

    TextColor(String code){
        this.code = code;
    }

}
