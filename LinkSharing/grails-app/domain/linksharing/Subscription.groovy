package linksharing

import groovy.transform.ToString
import linksharing.util.Seriousness

import java.time.LocalDate

@ToString
class Subscription {

    User user;
    Topic topic;
    Seriousness seriousness;
    Date dateCreated;
    Date lastUpdated;
    User createdBy;
    Boolean isSubscribed;

    static hasMany = [user:User]
    static belongsTo = [user:User,createdBy:User,topic:Topic]
    static constraints = {
        user(nullable: false,unique:['topic'])
        topic(nullable: false)
        seriousness(nullable: false)

        isSubscribed validator: {
            if (isSubscribed == true) return ['User Cannot Subscribed Again For same topic']
        }
    }
    static mapping = {
        table "subscription"
        autoTimestamp true
    }
}
