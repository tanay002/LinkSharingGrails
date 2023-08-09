package linksharing

import javax.servlet.http.HttpSession
import javax.transaction.Transactional

class UserController {

    def index() {

     //  HttpSession session= request.getSession(false);
       // User user = (User) session.getAttribute("user")

        render("User Dashboard Welcome +${session.username}") //+user.getUsername())
    }
    def getPrintedName()
    {
        def name=params.name;
        render("Welcome ${name}")
    }
    def getUserData() {
        def firstname=params.firstname;
        int id=params.id;
        render("Your firstname is ${firstname} and id is "+id)
    }

    def viewUser()
    {
        def userName= "Tanay Saxena";
        def userId =203;
        [user:userName,id:userId]
    }

    @Transactional
    def show(Long id)
    {
        Topic topic=Topic.findById(id);
        if(topic==null)
        {
            flash.error ="Topic does not exist with this id ${id}"
            redirect(controller:"login",action: "index");
        }
        else {
            if (topic.getVisibility()=="YES") {
                render("Success....Topic is public and Topic Name is ${topic.getTopicName()}")
            } else {
                User user = session.getAttribute("user");
                Subscription subscription= Subscription.findByTopicAndUser(topic,user);
                if(subscription!=null)
                    render("Success....Given Topic is Private and Subscribed by current Active User")
                else
                {
                    flash.error ="Topic is private and Current user is not subscribed to this topic"
                    redirect(controller:"login",action: "index");
                }

            }
        }
    }
}
