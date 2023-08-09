package linksharing

import groovy.transform.ToString

import java.time.LocalDate

@ToString
class ReadingItem implements  Serializable {

    Boolean isRead;
    Date dateCreated;
    Date lastUpdated;

   // static hasMany = [user:User,resource:Resource]
    static belongsTo = [createdBy:User,readingResource:Resource]

    static mapping = {
        table "reading_item"
        autoTimestamp true
      // id composite:['createdBy', 'readingResource']

    }

    static constraints = {
        createdBy(nullable: false)
        isRead(nullable: false)
        readingResource(nullable: false)  // ,unique: ['user']
    }

}
