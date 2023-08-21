package linksharing

import linksharing.util.Seriousness
import linksharing.util.VisibilityType

import javax.transaction.Transactional

class SubscriptionController {

    def index() { }

    @Transactional
    def save() {
        String email=session.email;
        if (session.email == null) {
            flash.error = "User does not exist in Session..Please Login First"
            render("Failure.......Inactive Session")
            redirect(controller: "login", action: "index");
        }
        int topicId = params.topicId;
        String seriousness = params.seriousness;
        Seriousness seriousness1=Seriousness.valueOf(seriousness);

        Topic topic=Topic.findById(topicId);
        User user = User.findByEmails(email);

        Subscription sub=new Subscription(user: user,topic: topic,seriousness: seriousness1);

        if (!sub.save(validate: true)) {
            sub.errors.allErrors.each {
                render flash.error = "Issue in creating Subscription ${it}"
            }
        } else {
            flash.message = "Success..... Subscription is created Successfully for user ${user.getUsername()} and topic ${topic.getTopicName()}"
            render "Success..... Subscription is created Successfully for user ${user.getUsername()} and topic ${topic.getTopicName()}"
        }
    }

    @Transactional
    def delete() {
        String email=session.email;
        if (session.email == null) {
            flash.error = "User does not exist in Session..Please Login First"
            render("Failure.......Inactive Session")
            redirect(controller: "login", action: "index");
        }
        int subscriptionId = params.id;
        Subscription subs=Subscription.findById(subscriptionId);

        if (!subs.delete(validate: true)) {
            subs.errors.allErrors.each {
                render flash.error = "Subscription Not Found for id ${subscriptionId} ${it}"
            }
        } else {
            flash.message = "Success..... Subscription is deleted Successfully....."
            render "Success..... Subscription is deleted Successfully....."
        }
    }

    @Transactional
    def update() {
        String email=session.email;
        if (session.email == null) {
            flash.error = "User does not exist in Session..Please Login First"
            render("Failure.......Inactive Session")
            redirect(controller: "login", action: "index");
        }

        int subscriptionId = params.subscriptionId;
        String seriousness = params.seriousness;
        Seriousness seriousness1=Seriousness.valueOf(seriousness);

        Subscription sub=Subscription.read(subscriptionId);
        if(sub!=null) {
            Subscription.executeUpdate("update Subscription s set s.seriousness='${seriousness1}'" +
                    "where s.id=${subscriptionId}");
            render("Subscription successfully Updated");
        }
        else {
            flash.error = "Subscription not found...."
            render("Subscription not found....")
        }
    }
}
