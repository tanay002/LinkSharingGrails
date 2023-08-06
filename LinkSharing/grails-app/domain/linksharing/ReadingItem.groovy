package linksharing

import groovy.transform.ToString

import java.time.LocalDate

@ToString
class ReadingItem implements  Serializable {

    Date dateCreated;
    Date lastUpdated;
    Boolean isRead;

   // static hasMany = [user:User,resource:Resource]
    static belongsTo = [readingUser:User,readingResource:Resource]

    static mapping = {
        table "reading_item"
        autoTimestamp true
        id composite:['readingUser', 'readingResource']
    }

    static constraints = {
        readingUser(nullable: false)
        isRead(nullable: false)
        readingResource(nullable: false)  // ,unique: ['user']
    }

}
