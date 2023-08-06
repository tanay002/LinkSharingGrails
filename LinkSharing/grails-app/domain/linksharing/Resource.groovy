package linksharing

import groovy.transform.ToString

import java.time.LocalDate

@ToString
abstract class Resource {

    String description;
    Date dateCreated;
    Date lastUpdated;

    static hasMany = [readingItems: ReadingItem,rating:Rating]
    static belongsTo = [createdBy:User,topic:Topic]

    static constraints = {
    }

    static mapping = {
        table "resources"
        autoTimestamp true
        description type: "text"
    }
}
