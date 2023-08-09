package linksharing

import javax.servlet.http.HttpSession

class ApplicationInterceptor {

    def loginInterceptor()
    {
        matchAll().excludes(controller: 'login')

    }

    boolean before() {
       // HttpSession session =request.getSession(false);
        if(session.username==null)
            redirect(controller: 'login', action: 'index')
        else
            redirect(controller: 'user', action: 'index')
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
