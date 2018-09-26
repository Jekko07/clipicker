package com.clipicker.me.web.form;

import javax.validation.constraints.NotNull;

public class DeleteAnnouncementForm {
    @NotNull(message = "required id")
    private Long id;

    public DeleteAnnouncementForm(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
