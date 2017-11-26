package com.spothero.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class ErrorBody {
    private final String code;
    private final String description;

    public ErrorBody(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @XmlElement(name = "code")
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @XmlElement(name = "description")
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }
}
