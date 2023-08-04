package linksharing

import groovy.transform.ToString

import java.time.LocalDate

@ToString
class ReadingItem {

    Date dateCreated;
    Date lastUpdated;
    User  readingUser;
    Boolean isRead;
    Resource resource;
    User user;

    static hasMany = [user:User,resource:Resource]
    static belongsTo = [User,Resource]

    static mapping = {
        table "reading_item"
        autoTimestamp true
    }

    static constraints = {
        user(nullable: false)
        isRead(nullable: false)
        resource(nullable: false,unique: ['user'])
    }

}
