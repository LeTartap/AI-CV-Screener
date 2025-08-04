package com.david.cv_screener_backend.controller;


import com.david.cv_screener_backend.dto.AnalysisResponse;
import com.david.cv_screener_backend.model.Candidate;
import com.david.cv_screener_backend.service.CandidateSaveService;
import com.david.cv_screener_backend.service.CvAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    private final CvAnalysisService analysisService;
    private final CandidateSaveService saveService;

    public CandidateController(CvAnalysisService analysisService, CandidateSaveService saveService) {
        this.analysisService = analysisService;
        this.saveService = saveService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Candidate> uploadCv(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("email") String email) throws IOException {

        AnalysisResponse analysis = analysisService.analyzeCv(file, name, email);
        String fakePath = "/tmp/" + file.getOriginalFilename(); // optional CV path
        Candidate saved = saveService.saveFromAnalysis(analysis, email, fakePath);
        return ResponseEntity.ok(saved);
    }
}
