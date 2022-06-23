package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.controller.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;

@Getter
@RequiredArgsConstructor
public enum State {

    OPEN("OPEN"),
    CLOSED("CLOSED");

    private static final Map<String, State> VALUE_TO_STATE =
        Arrays.stream(State.values())
            .collect(Collectors.toMap(State::getValue, s -> s));

    private final String value;

    @JsonCreator
    public static State fromString(String state) {
        return Optional.ofNullable(EnumUtils.getEnumIgnoreCase(State.class, state))
                       .orElseThrow(() -> new IllegalArgumentException("Illegal state " + state));
    }

    public static State fromValue(String value) {
        return VALUE_TO_STATE.getOrDefault(value, null);
    }
}