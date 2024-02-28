package br.com.nepomuceno.infrastructure.dto.response;

import java.util.List;

public class LinkResponse {

    private List<String> links;
    private Error error;

    public LinkResponse(List<String> links, Error error) {
        this.links = links;
        this.error = error;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
