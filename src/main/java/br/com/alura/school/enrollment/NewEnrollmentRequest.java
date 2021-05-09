package br.com.alura.school.enrollment;

import javax.validation.constraints.NotBlank;

class NewEnrollmentRequest {

    @NotBlank
    private String username;

    void setUsername(String username) {
        this.username = username;
    }

    String getUsername() {
        return username;
    }
}





