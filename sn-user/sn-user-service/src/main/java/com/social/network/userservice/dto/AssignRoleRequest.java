package com.social.network.userservice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.social.network.userservice.validation.AdditionalGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleRequest {
    @NotEmpty(message = "SN-001")
    @Pattern(regexp = "USER|ADMIN|SUPER_ADMIN", groups = AdditionalGroup.class)
    private String role;

    @NotEmpty(message = "SN-001")
    private String userId;

    public AssignRoleRequest(String s, String s1) {
    }

    public String getUserId() {
        return userId;
    }
}
