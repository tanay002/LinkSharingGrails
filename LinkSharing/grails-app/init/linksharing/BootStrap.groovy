package linksharing


import groovy.transform.CompileStatic
import linksharing.util.RoleType
import linksharing.util.Seriousness
import linksharing.util.VisibilityType

@CompileStatic
class BootStrap {

    def init = { servletContext ->
        List<User> users = createUsers()
        List<Topic> topic = createTopic()
        List<Subscription> subscription=doSubscription()
        List<Resource> resource=createResource()
        List<ReadingItem> readingItem=createReadingItems()
        List<Rating> rating=createResourceRatings()

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
                    username: "Tanay00${it}", mobNo: "887805670${it}", password: AppConstants.defaultPassword,
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

    List<Resource> createResource() {
        List<Resource> ResourceList=Resource.findAll();
        List<Topic> topicList=Topic.findAll();
        if(!ResourceList.isEmpty()) {
            log.error "Resources Already exist for each Topics in database ,So records can't be inserted"
            return null;
        }

        def counter=1;
        List<Resource> resourceList2 = []
        topicList.each { Topic topic ->
            (1..4).each { it ->
                Resource resource = null;
                if ("${it}" < 3) {
                    resource = new LinkResource(description: "Link Resource for Topic Name "+topic.getTopicName()+" and TopicId" + topic.getId() + "_Resource${it}", createdBy: topic.getCreatedBy(), topic: topic);
                    resource.setUrlLink("http://google.com");

                } else {
                    resource = new DocumentResource(description: "Document Resource for Topic Name "+topic.getTopicName()+" and TopicId" + topic.getId() + "_Resource${it}", createdBy: topic.getCreatedBy(), topic: topic);
                    resource.setFilePath("/home/tanay/Downloads/Link sharing Requirement.pdf");
                }

                if (resource.save()) {
                    resourceList2.add(resource)
                    log.info "Resource ${resource} saved successfully"
                    resource = null;
                } else {
                    log.error "Error saving Topic : ${topic.errors.allErrors}"
                }
                counter++;
            }
            counter = 1;
        }
        ResourceList
    }

    List<ReadingItem> createReadingItems()
    {
        List<ReadingItem> readingItemList=ReadingItem.findAll();
        List<ReadingItem> readingItemList2=[];
        if(!readingItemList.isEmpty()) {
            log.error "Reading Item is  Already exist for resources in database ,So records can't be inserted"
            return null;
        }
        List<User> userList=User.findAll();
         def user;
        List<Topic> topicList=Topic.findAll();
        List<Resource> ResourceList=Resource.findAll();
        for (Resource resource in ResourceList) {
            if (resource.getCreatedBy().getId() == 1)
                user = getById(2L)
            else
                user = getById(1L)

            ReadingItem readingItem = new ReadingItem(isRead: false, createdBy: user, readingResource: getByResourceId(resource.getId().toLong()));
            if (readingItem.save(validate:false)) {
                readingItemList2.add(readingItem)
                log.info "Reading Item ${readingItem}  saved successfully";
            } else {
                log.error "Error saving Reading Item  : ${readingItem.errors.allErrors}"
            }
        }
        readingItemList2
    }

    User getById(Long id) {
        log.info "#######Getting User with Id :${id}##########"
        User user = User.get(id)
        log.info "---------------------------------------------"
        user
    }

    Resource getByResourceId(Long id) {
        log.info "#######Getting Resource with Id :${id}##########"
        Resource resource = Resource.get(id)
        log.info "---------------------------------------------"
        resource
    }

    List<Subscription> doSubscription()
    {
            List<Subscription> subscriptionList= Subscription.findAll();
             List<Subscription> subList = [];
            if(!subscriptionList.isEmpty()) {
                log.error "Subscriptions Already exist in database ,So records can't be inserted"
            }
            else {
                List<Topic> topicList=Topic.findAll();

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
            }
        subList
    }

   List<Rating> createResourceRatings()
   {
       List<Rating> ratingListList=Rating.findAll();
       List<Rating> ratingItem2=[];
       if(!ratingListList.isEmpty()) {
           log.error "Rating Item is  Already exist for reading items in database ,So records can't be inserted"
           return null;
       }
       List<ReadingItem> readingItemList=ReadingItem.findAll();
       for(ReadingItem readingItem: readingItemList)
       {
           if(Boolean.FALSE==readingItem.getIsRead())
           {
              Rating rating  =new Rating(score: 4,resource: readingItem.getReadingResource(),createdby: readingItem.getCreatedBy() )
               if (rating.save()) {
                   ratingItem2.add(rating)
                   log.info "Rating Item ${rating}  saved successfully";
               } else {
                   log.error "Error saving rating Item  : ${rating.errors.allErrors}"
               }
            }
           else
            log.error("Rating is already given for resource "+readingItem.getReadingResource().getDescription())
           }
   }
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
