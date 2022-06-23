package cn.fluorescent.fluorescentmusic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlbumCreateRequest {

    private String name;

    private String description;

    private String cover_url;

    private String status;
}
