package com.clipicker.me.web.form;


import javax.validation.constraints.NotNull;
public class DeleteSymptomForm {

    @NotNull(message = "required name")
    private Long id;

    public DeleteSymptomForm(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
