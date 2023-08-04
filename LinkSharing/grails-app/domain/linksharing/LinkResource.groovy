package linksharing

import groovy.transform.ToString

import java.time.LocalDate

@ToString
class LinkResource extends Resource{

    String url;

    static constraints = {
        url(url: true)
    }
}
