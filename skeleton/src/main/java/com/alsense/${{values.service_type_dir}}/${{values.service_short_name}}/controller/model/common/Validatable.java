package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.controller.model.common;

import java.util.Set;

public interface Validatable {

    Set<ValidationIssue> getErrors();

    void setErrors(Set<ValidationIssue> errors);
}
