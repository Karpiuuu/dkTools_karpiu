package pl.dkcode.tools.handlers.enums;

public enum GroupType {

    CONSOLE("CONSOLE", 997, "&4&lCONSOLE", "&4&l"),
    ROOT("ROOT", 10, "&4&lROOT &7", "&c"),
    HEADADMIN("HEADADMIN", 9, "&4&lHEADADMIN &7", "&c"),
    ADMIN("ADMIN", 8, "&c&LADMIN &7", "&c"),
    MOD("MOD", 7, "&2&lMODERATOR &7", "&a"),
    HELPER("HELPER", 6, "&b&lHELPER &7", "&b"),
    SVIP("SVIP", 5, "&9&lSVIP &7", "&e"),
    VIP("VIP",2, "&eVIP &7", "&e"),
    PLAYER("PLAYER",1,"&7","&7");

    private String name;
    private int level;
    private  String prefix;
    private String suffix;

    GroupType(String name, int level, String prefix, String suffix){
        this.name = name;
        this.level = level;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }


}
