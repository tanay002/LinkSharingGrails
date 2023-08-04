package linksharing

class UserController {

    def index() {
        render("This is basic user controller page")
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
