package linksharing

class Roles {
    String roleType;

    static hasMany = [user:User]
    static belongsTo = [User]

    static constraints = {
        roleType(nullable: false)
    }
}
