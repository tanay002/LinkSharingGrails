package linksharing

import grails.events.annotation.Subscriber
import groovy.transform.ToString
import linksharing.util.Seriousness
import linksharing.util.VisibilityType
import org.hibernate.event.spi.PostInsertEvent

import java.time.LocalDate

@ToString
class Topic {

    String topicName
    VisibilityType visibility
    Date dateCreated
    Date lastUpdated

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
        sort topicName:"asc"
    }

    String toString()
    {
        return "Topic Name: "+topicName+" Username: "+createdBy.getUsername()
    }

    def afterInsert() {
        def session1 = Subscription.withNewSession { session->
             HashMap <String,Integer> hashmap=new HashMap<String,Integer>();
           //  hashmap.put("max",100);
           //  hashmap.put("offset",0);
             List<Subscription> subList = [];
             String query = "from Topic as t where t.id not in (select sub.topic from Subscription as sub)"
             List<Topic> topicList = Topic.findAll(query, hashmap);
                 for (Topic topic in topicList) {
                     Subscription sub = new Subscription(seriousness: Seriousness.SERIOUS,
                             topic: topic, user: topic.getCreatedBy());
                     if (sub.save()) {
                         subList.add(sub)
                         log.info "Subscription ${sub} saved successfully for Topic " + topic.getTopicName()
                     } else {
                         log.error "Error saving Subscription : ${sub.errors.allErrors}"
                     }
                 }
                  subList;
             }
    }
}
