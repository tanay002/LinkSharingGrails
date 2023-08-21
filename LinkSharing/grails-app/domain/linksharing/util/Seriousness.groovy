package linksharing.util

enum Seriousness {
    SERIOUS,CASUAL,VERY_SERIOUS

    static Seriousness getType(String type)
    {
        Seriousness seriousnessType = valueOf(Seriousness.class, type);
        return seriousnessType;
    }
}