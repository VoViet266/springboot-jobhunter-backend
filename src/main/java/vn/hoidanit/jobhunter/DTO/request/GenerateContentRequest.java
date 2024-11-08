package vn.hoidanit.jobhunter.DTO.request;

import java.util.List;

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

    public static class Content {
        private String role;
        private List<Part> parts;

        public Content() {
        }

        public Content(List<Part> parts) {
            this.parts = parts;
        }

        public List<Part> getParts() {
            return parts;
        }

        public void setParts(List<Part> parts) {
            this.parts = parts;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
        
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
