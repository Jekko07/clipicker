package com.clipicker.me.web.form;

import javax.validation.constraints.NotNull;
public class SpecForm {
    private Long id;
    @NotNull(message = "required name")
    private String name;
    @NotNull(message = "required description")
    private String description;

    public SpecForm(){ }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
