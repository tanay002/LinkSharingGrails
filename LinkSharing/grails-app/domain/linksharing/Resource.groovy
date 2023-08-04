package linksharing

import groovy.transform.ToString

import java.time.LocalDate

@ToString
abstract class Resource {

    String description;
    Date dateCreated;
    Date lastUpdated;
    User createdBy;
    Topic topic;
    ReadingItem readingItems;
    Rating rating;

    static hasMany = [readingItems: ReadingItem,rating:Rating]
    static belongsTo = [createdBy:User,topic:Topic]

    static constraints = {
    }

    static mapping = {
        table "resource"
        autoTimestamp true
        description type: "text"
    }
}
