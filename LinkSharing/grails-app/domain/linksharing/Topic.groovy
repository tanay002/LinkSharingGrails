package linksharing

import groovy.transform.ToString
import linksharing.util.VisibilityType

import java.time.LocalDate

@ToString
class Topic {

    String topicName;
    VisibilityType visibility;
    Date dateCreated;
    Date lastUpdated;

    static hasMany = [Subscription,Resource,User]
    static belongsTo = [createdBy:User]

    static constraints = {
        topicName(nullable: false,blank: false,unique: true)
        visibility(nullable: false)
        createdBy(nullable: false)
    }
    static mapping = {
        table "topic"
        autoTimestamp true
    }
}
