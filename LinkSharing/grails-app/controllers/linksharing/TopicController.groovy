package linksharing

import linksharing.util.VisibilityType

import javax.transaction.Transactional

import static linksharing.util.VisibilityType.YES

class TopicController {

    def index() {}
    static allowedMethods = [save: 'POST', show: 'GET'];

    @Transactional
    def save() {
        if (session.email == null) {
            flash.error = "User does not exist in Session..Please Login First"
            render("Failure.......Inactive Session")
        }
        String email = session.email;
        User user = User.findByEmails(email);
        Topic topic = new Topic(topicName: params.topicName,
                visibility: VisibilityType.YES, createdBy: user);

        if (!topic.save(validate: true)) {
            topic.errors.allErrors.each {
                render flash.error = "Issue in creating topic ${it}"
            }
        } else {
            flash.message = "Success..... Topic is created Successfully"
            render "Success..... Topic is created Successfully"
        }
    }

    @Transactional
    def show(Long id) {

        if (session.email == null) {
            flash.error = "User does not exist in Session..Please Login First"
            render("Failure.......Inactive Session")
            redirect(controller: "login", action: "index");
        }
        Topic topic = Topic.read(id);
        if (topic == null) {
            flash.error = "Topic does not exist with this id ${id}"
            redirect(controller: "login", action: "index");
        } else {
            if (topic.getVisibility() == "YES") {
                render("Success....Topic is public and Topic Name is ${topic.getTopicName()}")
            } else {
                String email = session.email
                Subscription subscription = Subscription.findByTopicAndUser(topic, User.findByEmails(email));
                if (subscription != null)
                    render("Success....Given Topic is Private and Subscribed by current Active User")
                else {
                    flash.error = "Topic is private and Current user is not subscribed to this topic"
                    redirect(controller: "login", action: "index");
                }
            }
        }
    }


    @Transactional
    def delete(Long id) {

        if (session.email == null) {
            flash.error = "User does not exist in Session..Please Login First"
            render("Failure.......Inactive Session")
        }

        Topic topic = Topic.load(id);
        if (topic == null) {
            flash.error = "Topic does not exist with this id ${id}"
            redirect(controller: "login", action: "index");
        } else {
            List<Subscription> subscription = Subscription.findAllByTopic(topic);
            List<Resource> resources = Resource.findAllByTopic(topic);

            for (Subscription sub : subscription)
                Subscription.load(sub.getId()).delete();

            for (Resource res : resources)
                Resource.load(res.getId()).delete();

            Topic.load(topic.getId()).delete();
        }
    }

}