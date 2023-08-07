package linksharing

import grails.events.annotation.Subscriber
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import linksharing.util.RoleType
import linksharing.util.Seriousness
import linksharing.util.VisibilityType
import org.hibernate.event.spi.PostInsertEvent

@CompileStatic
class BootStrap {

    def init = { servletContext ->
        List<User> users = createUsers()
        List<Topic> topic = createTopic()

    //   validate(users.first())
    }

    List<User> createUsers() {
        List<User> userList=User.findAll();
        if(!userList.isEmpty()) {
            log.error "User Already exist in database ,So records can't be inserted"
            return null;
        }
        List<User> users = []
        (1..2).each {
            RoleType roleType=RoleType.NORMAL_USER
            if("${it}"==2)
                roleType=RoleType.ADMIN

            User user = new User(firstName: "Tanay${it}", lastName: "Saxena${it}", emails: "tanay${it}@yopmail.com",
                    username: "Tanay00${it}", mobNo: "887805670${it}", password: "Tanay${it}",
                    isActive: true, dateCreated: new Date(), lastUpdated: new Date(),
                    roles: Arrays.asList(new Roles(roleType: roleType)));

            if (user.save()) {
                users.add(user)
                log.info "User ${user} saved successfully"
            } else {
                log.error "Error saving user : ${user.errors.allErrors}"
            }
        }
    //    userList=users;
        users
    }
    List<Topic> createTopic() {
        List<User> userList=User.findAll();
        List<Topic> topicList=Topic.findAll();
        if(!topicList.isEmpty()) {
            log.error "Topics Already exist for users in database ,So records can't be inserted"
            return null;
        }

        def counter=1;
        List<Topic> topicList2 = []
        for (User user: userList) {
            (1..5).each {
                Topic topic = new Topic(topicName: "ExerciseByUser"+user.getId()+"_"+ counter, visibility: VisibilityType.YES, dateCreated: new Date(),
                        lastUpdated: new Date(), createdBy: user);
                if (topic.save()) {
                    topicList2.add(topic)
                    log.info "Topic ${topic} saved successfully"
                } else {
                    log.error "Error saving Topic : ${topic.errors.allErrors}"
                }
                counter++;
            }
            counter=1;
        }
       // topicList=topicList2;
        topicList2
    }

   // @Subscriber
  /*  def afterInsert(PostInsertEvent event)
    {
        Subscription.withNewSession { session ->
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
    */
   
/*
    void validate(User user) {
        log.info "Before the value set HasErrors ---${user.hasErrors()}"
        user.name = null
        log.info "---After value set HasErrors---${user.hasErrors()}"
        log.info "---User is valid :: ${user.validate()}"
        log.info "---User email is valid :: ${user.validate(['email'])}"
        log.info "---After validate HasErrors---${user.hasErrors()}"
        user.save(validate: false)
    }*/
    def destroy = {
    }
}
