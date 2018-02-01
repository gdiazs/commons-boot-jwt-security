package io.gdiazs.commons.boot.security.authentication;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

class Ping {
    private Long currentTime;

    public Ping() {
        this.currentTime = System.currentTimeMillis();
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }
}

@RestController
class PingController{

    @GetMapping
    @RequestMapping("/ping")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Ping> get(){
        return ResponseEntity.ok().body(new Ping());
    }


    @GetMapping
    @RequestMapping("/pingError")
    @PreAuthorize("hasRole('ROLE_SYSADMIN')")
    public ResponseEntity<Ping> getError(){
        return ResponseEntity.ok().body(new Ping());
    }

}