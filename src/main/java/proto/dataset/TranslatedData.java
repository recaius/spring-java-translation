package proto.dataset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonTypeInfo(use= JsonTypeInfo.Id.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TranslatedData {
    private ArrayList<ArrangedText> arrangements;
    private ArrayList<TranslatedText> translations;
}
