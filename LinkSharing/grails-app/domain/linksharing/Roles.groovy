package linksharing

import linksharing.util.RoleType

class Roles {
    RoleType roleType;
    Date dateCreated;
    Date lastUpdated;

    static hasMany = [User]
    static belongsTo = [User]

    static constraints = {
        roleType(nullable: false)
    }
}
