package linksharing

class LoginCheckInterceptor {

    boolean before() {
        matchAll().excludes(controller: 'login')

    }
}
