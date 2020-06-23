package com.social.network.userservice.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, AdditionalGroup.class})
public interface ValidationOrder {
}
