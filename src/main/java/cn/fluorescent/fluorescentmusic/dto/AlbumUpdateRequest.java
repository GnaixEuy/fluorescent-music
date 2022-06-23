package cn.fluorescent.fluorescentmusic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlbumUpdateRequest {
    private String name;
    private String description;
    private String coverUrl;
    private String status;
}
