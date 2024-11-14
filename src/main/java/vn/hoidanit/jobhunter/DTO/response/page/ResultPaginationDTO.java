package vn.hoidanit.jobhunter.DTO.response.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResultPaginationDTO {
    private Meta meta;
    private Object result;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meta {
        private Long total;
        private int page;
        private int pageSize;
        private int pages;
    }
}
