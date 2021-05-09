package br.com.alura.school.enrollment;

import javax.validation.constraints.NotBlank;

public class NewEnrollmentRequest {

    @NotBlank
    private String username;

    public NewEnrollmentRequest() {
    }

    public NewEnrollmentRequest(String username) {
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


}





