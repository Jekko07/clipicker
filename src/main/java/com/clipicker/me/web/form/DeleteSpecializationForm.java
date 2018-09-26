package com.clipicker.me.web.form;

import javax.validation.constraints.NotNull;
public class DeleteSpecializationForm {
    @NotNull(message = "required id")
    private Long id;

    public DeleteSpecializationForm(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
