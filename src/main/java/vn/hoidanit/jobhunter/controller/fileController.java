package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import vn.hoidanit.jobhunter.service.fileService;
import vn.hoidanit.jobhunter.DTO.response.file.ResUploadFileDTO;

@RestController
@RequestMapping("/api/v1")
public class fileController {
    @Value("${hoidanit.upload-file.base-uri}")
    private String baseURI;
    private final fileService fileService;

    public fileController(fileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/files")
    public ResponseEntity<ResUploadFileDTO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder) throws IOException, Exception {
        if (file.isEmpty() || file == null) {
            throw new Exception("File is empty");
        }
        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "pdf", "doc", "docx");
        Boolean isAllowed = allowedExtensions.stream().anyMatch(item -> fileName.toLowerCase().endsWith(item));
        
        if (!isAllowed) {
            throw new Exception("File not allowed");
        }
        this.fileService.createDirectory(baseURI + folder);
        String uploadFile = this.fileService.store(file, folder);
        ResUploadFileDTO resUploadFileDTO = new ResUploadFileDTO( uploadFile, Instant.now());
        return ResponseEntity.ok(resUploadFileDTO);
    }
}
