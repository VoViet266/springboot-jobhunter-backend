package vn.hoidanit.jobhunter.DTO;

import java.util.List;

import vn.hoidanit.jobhunter.DTO.GenerateContentRequest.Content;

public class GenerateContentResponse {
    private List<Candidate> candidates;
    // private UsageMetadata usageMetadata;
    // private String modelVersion;

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    // public UsageMetadata getUsageMetadata() {
    //     return usageMetadata;
    // }

    // public void setUsageMetadata(UsageMetadata usageMetadata) {
    //     this.usageMetadata = usageMetadata;
    // }

    // public String getModelVersion() {
    //     return modelVersion;
    // }

    // public void setModelVersion(String modelVersion) {
    //     this.modelVersion = modelVersion;
    // }

    public static class Candidate {
        private Content content;
        // private String finishReason;
        // private int index;
        // private List<SafetyRating> safetyRatings;

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }

        // public String getFinishReason() {
        //     return finishReason;
        // }

        // public void setFinishReason(String finishReason) {
        //     this.finishReason = finishReason;
        // }

        // public int getIndex() {
        //     return index;
        // }

        // public void setIndex(int index) {
        //     this.index = index;
        // }

        // public List<SafetyRating> getSafetyRatings() {
        //     return safetyRatings;
        // }

        // public void setSafetyRatings(List<SafetyRating> safetyRatings) {
        //     this.safetyRatings = safetyRatings;
        // }
    }

    // public static class SafetyRating {
    //     private String category;
    //     private String probability;

    //     public String getCategory() {
    //         return category;
    //     }

    //     public void setCategory(String category) {
    //         this.category = category;
    //     }

    //     public String getProbability() {
    //         return probability;
    //     }

    //     public void setProbability(String probability) {
    //         this.probability = probability;
    //     }
    // }

    // public static class UsageMetadata {
    //     private int promptTokenCount;
    //     private int candidatesTokenCount;
    //     private int totalTokenCount;

    //     public int getPromptTokenCount() {
    //         return promptTokenCount;
    //     }

    //     public void setPromptTokenCount(int promptTokenCount) {
    //         this.promptTokenCount = promptTokenCount;
    //     }

    //     public int getCandidatesTokenCount() {
    //         return candidatesTokenCount;
    //     }

    //     public void setCandidatesTokenCount(int candidatesTokenCount) {
    //         this.candidatesTokenCount = candidatesTokenCount;
    //     }

    //     public int getTotalTokenCount() {
    //         return totalTokenCount;
    //     }

    //     public void setTotalTokenCount(int totalTokenCount) {
    //         this.totalTokenCount = totalTokenCount;
    //     }
    // }
}
