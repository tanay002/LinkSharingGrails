package linksharing

import javax.servlet.http.HttpSession
import javax.transaction.Transactional

class LoginController {

    UserService userService;

    def index() {
    //    HttpSession session=request.getSession(false);
        if(session.username==null)
            render ("Failure.......User does not exist in Session")
        else
            forward controller: "user", action: "index"
    }

    @Transactional
    def loginHandler(String userName ,String password)
    {
        User user=User.findByUsernameAndPassword(userName,password)
        if(user!=null && user.isActive==true)
        {
         //   HttpSession session =request.getSession(true);
          //  session.setAttribute("user",user);
            session.username="${user.username}"
            redirect(action: "index");
        }
        else if(user!=null)
        {
            flash.error= "Your account is not active"
            render "Failure......Account is Inactive"
        }
        else
        {
            render flash.error= "User not found"
        }

    }

    def logout() {
        log.info "User agent: " + request.getHeader("User-Agent")
        session.invalidate()
        forward action: "index"
    }
}
