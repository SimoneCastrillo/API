package buffet.app_web.config;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Google {
    private String summary;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
