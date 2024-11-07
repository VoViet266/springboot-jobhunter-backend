package vn.hoidanit.jobhunter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vn.hoidanit.jobhunter.dto.GenerateContentRequest;
import vn.hoidanit.jobhunter.dto.GenerateContentResponse;
import vn.hoidanit.jobhunter.entity.Conversation;
import vn.hoidanit.jobhunter.repository.ConversationRepository;

@Service
public class GoogleGenerativeLanguageService {
    private final ConversationRepository conversationRepository;
    private final RestTemplate restTemplate;
    @Value("${google.api.key}")
    private String apiKey;
    public GoogleGenerativeLanguageService(RestTemplate restTemplate, ConversationRepository conversationRepository) {
        this.restTemplate = restTemplate;
        this.conversationRepository = conversationRepository;
    }
    public Conversation saveChathistory(Conversation conversation){
      return this.conversationRepository.save(conversation);
    }
    // Sử dụng RestTemplate để gửi request lên google api và nhận response
    public GenerateContentResponse generateContent(GenerateContentRequest request) {
        /// thiết lập url để gửi request lên google api với key 
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key="
                + apiKey;
        /// thiết lập header cho request với content type là application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        /// tạo request body từ text và header vừa thiết lập
        HttpEntity<GenerateContentRequest> entity = new HttpEntity<>(request, headers);
        System.out.println("Sending request to google api");
        // gửi request lên google api và nhận response
        return restTemplate.exchange(url, HttpMethod.POST, entity, GenerateContentResponse.class).getBody();
    }
}
