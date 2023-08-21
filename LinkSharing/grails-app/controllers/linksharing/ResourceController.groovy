package linksharing

import org.hibernate.ObjectNotFoundException

import javax.transaction.Transactional

class ResourceController {

    def index() { }

    @Transactional
    def delete(Long id) {
        try {
            if (session.email == null) {
                flash.error = "User does not exist in Session..Please Login First"
                render("Failure.......Inactive Session")
                redirect(controller: "login", action: "index");
            }
            Resource resource = Resource.load(id);
            if(resource.getId()!=null)
                Resource.load(resource.getId()).delete();
            flash.message = "Success..... Resource is deleted Successfully"
            render "Success..... Resource is deleted Successfully"

        }
         catch (ObjectNotFoundException e)
        log.error("Resource does not exist with ${id}")
        flash.error="Resource does not exist with ${id}";
    }
}
