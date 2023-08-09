package linksharing

import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
class UserService {

  //  @Transactional(readOnly = true)
 /*   User validateUser(String username,String password)
    {

    }
   */
    def serviceMethod() {

    }

  /*  def validateUser(params) {
        User user=User.findByUsernameAndPassword(params.user,password);
        user
    }

   */
}
