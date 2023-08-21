package linksharing.util

enum VisibilityType {
    YES,NO

    static VisibilityType getType(String type)
    {
        VisibilityType visibilityType = valueOf(VisibilityType.class, type);
        return visibilityType;
    }
}