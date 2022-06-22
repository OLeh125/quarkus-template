package ${{values.service_root_folder}}.controller.model.common;

import java.util.Set;

public interface Validatable {

    Set<ValidationIssue> getErrors();

    void setErrors(Set<ValidationIssue> errors);
}
