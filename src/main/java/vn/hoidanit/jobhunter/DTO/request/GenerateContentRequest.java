package vn.hoidanit.jobhunter.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GenerateContentRequest {
    private List<Content> contents;

    // public GenerateContentRequest(String text) {

        
    // }
    // public GenerateContentRequest() {
    // }
    public GenerateContentRequest(List<Content> contents) {
        this.contents = contents;
    }
    public GenerateContentRequest() {
    }
    public List<Content> getContents() {
        return contents;
    }
   
    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        private String role;
        private List<Part> parts;


        
    public static class Part {
        private String text;

        public Part(String text) {
            this.text = text;
        }

        public Part() {
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
    }

}
