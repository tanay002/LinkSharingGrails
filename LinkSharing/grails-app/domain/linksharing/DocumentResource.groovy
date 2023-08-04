package linksharing

import groovy.transform.ToString

import java.time.LocalDate

@ToString
class DocumentResource extends  Resource {

    String filePath;
    static constraints = {
    }
}
