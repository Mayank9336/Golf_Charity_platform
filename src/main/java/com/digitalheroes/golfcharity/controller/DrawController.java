package com.digitalheroes.golfcharity.controller;

import com.digitalheroes.golfcharity.dto.DrawResultDto;
import com.digitalheroes.golfcharity.model.DrawMode;
import com.digitalheroes.golfcharity.service.DrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/draw")
@RequiredArgsConstructor
@CrossOrigin
public class DrawController {
    private final DrawService drawService;

    @PostMapping("/run")
    @PreAuthorize("hasRole('ADMIN')")
    public DrawResultDto runDraw(@RequestParam DrawMode mode) {
        return drawService.runDraw(mode);
    }
}
