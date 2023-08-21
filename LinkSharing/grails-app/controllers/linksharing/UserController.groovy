package linksharing

import javax.servlet.http.HttpSession
import javax.transaction.Transactional

class UserController {

    def index() {
        render("User Dashboard Welcome +${session.email}") //+user.getUsername())
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
}
