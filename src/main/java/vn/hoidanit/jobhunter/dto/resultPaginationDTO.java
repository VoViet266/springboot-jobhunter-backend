package vn.hoidanit.jobhunter.dto;

public class resultPaginationDTO {
    private Meta meta;
    private Object result;
    public Meta getMeta() {
        return meta;
    }
    public void setMeta(Meta meta) {
        this.meta = meta;
    }
    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }
    public static class Meta {
        private Long total;
        private int page;
        private int pageSize;
        private int pages;
        public Meta() {
        }
        public Meta(Long total, int page, int pageSize, int pages) {
            this.total = total;
            this.page = page;
            this.pageSize = pageSize;
            this.pages = pages;
        }
        public Long getTotal() {
            return total;
        }
        public void setTotal(Long total) {
            this.total = total;
        }
        public int getPage() {
            return page;
        }
        public void setPage(int page) {
            this.page = page;
        }
        public int getPageSize() {
            return pageSize;
        }
        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
        public int getPages() {
            return pages;
        }
        public void setPages(int pages) {
            this.pages = pages;
        }
    }
}
