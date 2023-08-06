package linksharing

import groovy.transform.ToString

import java.time.LocalDate

@ToString
class User {

    String firstName;
    String lastName;
    String emails;
    String username;
    String mobNo;
    String password;
    Boolean isActive;
    byte[] photo;
    Date dateCreated;
    Date lastUpdated;

    static transients = ['name']

    static hasMany = [topic:Topic,subscription: Subscription,resource:Resource,readingItem:ReadingItem,roles: Roles]

    String getName() {
        return "$firstName $lastName"
    }

    void setName(String name) {
        firstName = name.split(" ")[0]
        lastName = name.split(" ")[1]
    }

    static constraints = {
        emails(unique: true,nullable: false,email:true,blank: false)
        password(nullable: false,minSize: 5,blank: false)
        firstName(nullable: false,blank: false)
        lastName(nullable: false,blank: false)
        isActive(nullable: false)
        photo(nullable: false)
    }

    static mapping = {
        content (sqlType: "longblob")
        table "users"
        autoTimestamp true
    }


}
