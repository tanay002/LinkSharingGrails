package linksharing

import groovy.transform.ToString

import java.time.LocalDate

@ToString
class Rating implements  Serializable {

    Integer score;
    Date dateCreated;
    Date lastUpdated;

  //  static hasMany = [ratingByUser:User,resource: Resource]
    static belongsTo = [resource:Resource,createdby:User]

    static constraints = {
        createdby(nullable:false)
        resource(nullable:false) // ,unique: ['ratingByUser']
        score(nullable:false,size: 1..5)

    }


    static mapping = {
         table "rating"
          autoTimestamp true
   //     id composite:['ratingByUser', 'resource']
    }
}
