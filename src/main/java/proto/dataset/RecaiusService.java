package proto.dataset;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonTypeInfo(use= JsonTypeInfo.Id.NONE)
public class RecaiusService {
    private User machine_translation;
}
