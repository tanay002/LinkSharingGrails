package linksharing

import groovy.transform.ToString

import java.time.LocalDate

@ToString
class Rating {

    User createdBy;
    Integer score;
    Resource resource;
    Date dateCreated;
    Date lastUpdated;
    User ratingByUser;

    static hasMany = [ratingByUser:User,resource: Resource]
    static belongsTo = [createdBy:User,resource:Resource,ratingByUser:User]

    static constraints = {
        createdBy(nullable:false)
        resource(nullable:false,unique: ['ratingByUser'])
        score(nullable:false,size: 1..5)

    }


    static mapping = {
        table "resource_rating"
        autoTimestamp true
    }
}
