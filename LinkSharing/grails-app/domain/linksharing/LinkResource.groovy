package linksharing

import groovy.transform.ToString

import java.time.LocalDate

@ToString
class LinkResource extends Resource{

    String urlLink;

    static constraints = {
        urlLink(url: true)
    }

    String toString()
    {
        return "URL Link: "+urlLink;
    }
}
