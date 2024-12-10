package vn.hoidanit.jobhunter.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.hoidanit.jobhunter.dto.response.file.ResUploadFileDTO;
import vn.hoidanit.jobhunter.service.fileService;

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
        if (file.isEmpty()) {
            throw new Exception("File is empty");
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new Exception("File name is null");
        }
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "pdf", "doc", "docx");
        Boolean isAllowed = allowedExtensions.stream().anyMatch(item -> fileName.toLowerCase().endsWith(item));
        if (!isAllowed) {
            throw new Exception("File not allowed");
        }

        
        this.fileService.createDirectory(baseURI + folder);
        String uploadFile = this.fileService.store(file, folder);
        ResUploadFileDTO resUploadFileDTO = new ResUploadFileDTO(uploadFile, Instant.now());
        return ResponseEntity.ok(resUploadFileDTO);
    }

    @GetMapping("/files")
    public ResponseEntity<Resource> downloadFile(
            @RequestParam("folder") String folder,
            @RequestParam("fileName") String fileName) throws IOException, Exception {
        {
            if (fileName == null || folder == null) {
                throw new Exception("File not found");
            }
            long fileLength = this.fileService.getFileLength(folder, fileName);
            if (fileLength == 0) {
                throw new Exception("File not found");
            }

            InputStreamResource resource = this.fileService.getResource(folder, fileName);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .contentLength(fileLength)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
    }
}
