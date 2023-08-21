package linksharing

import grails.converters.JSON
import linksharing.util.RoleType

import javax.servlet.http.HttpSession
import javax.transaction.Transactional

class LoginController {

    static allowedMethods = [register:'POST',"loginHandler":'POST'];

    def index() {
        if(session.email==null)
            render ("Failure.......User does not exist in Session")
        else
            forward controller: "user", action: "index"
    }

    @Transactional
    def loginHandler()
    {
        User user=User.findByUsernameAndPassword(params.userName,params.password)
        if(user!=null && user.isActive==true)
        {
            session.email=user.getEmails()
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

    @Transactional
    def register() {
        def user= new User(params);
        println user;
        user.setIsActive(true);
        Roles role=new Roles(roleType: RoleType.NORMAL_USER).save();
        user.setRoles(Arrays.asList(role))
        if (!user.save(validate:true)) {
            user.errors.allErrors.each {
                render flash.error = "Issue in registering user ${it}"
            }
        }
        else
        {
         render "Success..... User is registered Successfully"
        }
    }
    def logout() {
        log.info "User agent: " + request.getHeader("User-Agent")
        session.invalidate()
        forward action: "index"
    }
}
