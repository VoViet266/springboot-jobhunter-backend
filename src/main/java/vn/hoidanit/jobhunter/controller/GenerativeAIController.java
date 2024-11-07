package vn.hoidanit.jobhunter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.dto.GenerateContentRequest;
import vn.hoidanit.jobhunter.dto.GenerateContentResponse;
import vn.hoidanit.jobhunter.service.GoogleGenerativeLanguageService;

@RestController
@RequestMapping("/api/v1")
public class GenerativeAIController {
    private final ArrayList<GenerateContentRequest.Content> chatHistory = new ArrayList<>();
    private final GoogleGenerativeLanguageService googleGenerativeLanguageService;
    public GenerativeAIController(GoogleGenerativeLanguageService googleGenerativeLanguageService) {
        this.googleGenerativeLanguageService = googleGenerativeLanguageService;
    }
    // @PostMapping("/generate")
    // public GenerateContentResponse generateContent(@RequestBody GenerateContentRequest request) {
    //     return googleGenerativeLanguageService.generateContent(request);
    // }
    @PostMapping("/conversation")
    public GenerateContentResponse sendMessage(@RequestBody GenerateContentRequest userMessage) {
        // Thêm tin nhắn của người dùng vào lịch sử cuộc trò chuyện với vai trò "user"
        GenerateContentRequest.Content userContent = new GenerateContentRequest.Content();
        GenerateContentRequest.Content.Part userPart = new GenerateContentRequest.Content.Part();
        userContent.setParts(List.of(userPart));
        userContent.setRole("user");
        userPart.setText(userMessage.getContents().get(0).getParts().get(0).getText());
        chatHistory.add(userContent);
        // Tạo yêu cầu mới với lịch sử cuộc trò chuyện đã cập nhật
        GenerateContentRequest request = new GenerateContentRequest();
        request.setContents(chatHistory);
        // Tạo phản hồi từ dịch vụ GoogleGenerativeLanguageService
        GenerateContentResponse response = googleGenerativeLanguageService.generateContent(request);
        // Kiểm tra xem phản hồi từ API có hợp lệ không
        if (response != null && response.getCandidates() != null && !response.getCandidates().isEmpty()) {
            // model
            String modelResponse = response.getCandidates().get(0).getContent().getParts().get(0).getText();
            GenerateContentRequest.Content.Part modelPart = new GenerateContentRequest.Content.Part();
            modelPart.setText(modelResponse);
            GenerateContentRequest.Content botContent = new GenerateContentRequest.Content();
            botContent.setParts(List.of(modelPart));
            botContent.setRole("model");
            chatHistory.add(botContent);
        }
        // this.googleGenerativeLanguageService.saveChathistory(chatHistory);
        return response;
    }

}
