package linksharing

import groovy.transform.ToString
import linksharing.util.Seriousness

import java.time.LocalDate

@ToString
class Subscription implements Serializable {


    Seriousness seriousness;
    Date dateCreated;
    Date lastUpdated;
    User user;
    Topic topic;

    static belongsTo = [user:User,topic:Topic]
    static constraints = {
        user(nullable: false,unique:['topic'])
        topic(nullable: false)
        seriousness(nullable: false,defaultValue: Seriousness.SERIOUS)

    }
    static mapping = {
        table "subscription"
        autoTimestamp true
        id composite:['user', 'topic']
        topic lazy: false
        user lazy: false
    }
}
