package proto.dataset;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonTypeInfo(use= JsonTypeInfo.Id.NONE)
public class Query {
    private final String query;
    private String mode = "spoken_language";
    private String src_lang = "ja";
    private String tgt_lang = "en";
    private boolean arrange = true;
}
