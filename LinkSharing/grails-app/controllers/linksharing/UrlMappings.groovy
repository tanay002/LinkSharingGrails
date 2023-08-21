package linksharing

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
    /*    "/reg/"(controller: "login", action: "register")
        "/view/"(controller: "topic", action: "show")
        "/"(controller: 'login', action: 'index')
        "/create/"(controller: 'topic', action: 'save')
        "/log"(controller: 'login', action: 'loginHandler')
*/
     //   "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
