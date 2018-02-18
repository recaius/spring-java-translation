package proto.dataset;

import lombok.Data;

@Data
public class Token {
    private long expiry_sec;
    private String token;
}
