package linksharing

import groovy.transform.ToString
import linksharing.util.Seriousness

import java.time.LocalDate

@ToString
class Subscription implements Serializable {


    Seriousness seriousness;
    Date dateCreated;
    Date lastUpdated;
    Boolean isSubscribed;
    User user;
    Topic topic;

    static belongsTo = [user:User,topic:Topic]
    static constraints = {
        user(nullable: false,unique:['topic'])
        topic(nullable: false)
        seriousness(nullable: false)

    /*    isSubscribed validator: {
            if (isSubscribed == true) return ['User Cannot Subscribed Again For same topic']
        }
        */
    }
    static mapping = {
        table "subscription"
        autoTimestamp true
        id composite:['user', 'topic']
    }
}
