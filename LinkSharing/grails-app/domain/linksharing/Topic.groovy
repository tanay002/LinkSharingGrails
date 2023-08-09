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
    }

    String toString()
    {
        return "Topic Name: "+topicName+" Username: "+createdBy.getUsername()
    }

    def afterInsert() {
        Subscription.withNewSession { session->
            List<Subscription> subscriptionList= Subscription.findAll();
            if(!subscriptionList.isEmpty()) {
                log.error "Subscriptions Already exist in database ,So records can't be inserted"
            }
            else {
                List<Topic> topicList=Topic.findAll();
                List<Subscription> subList = [];
                for (Topic topic in topicList) {
                    Subscription sub = new Subscription(seriousness: Seriousness.SERIOUS, isSubscribed: true,
                            topic: topic, user: topic.getCreatedBy());
                    if (sub.save()) {
                        subList.add(sub)
                        log.info "Subscription ${sub} saved successfully for Topic " + topic.getTopicName()
                    } else {
                        log.error "Error saving Subscription : ${sub.errors.allErrors}"
                    }
                }
                subscriptionList = subList;
            }
        }
    }
}
