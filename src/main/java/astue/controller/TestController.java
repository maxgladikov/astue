package astue.controller;

import astue.model.Ied;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

//    @PutMapping(path="{id}",
//            consumes =  "application/json"
//    )
//    public ResponseEntity<Void> put(@RequestBody Ied ied, @PathVariable Long id){
//        System.out.println("id:"+id);
//        System.out.println(ied);
//        return ResponseEntity.ok().build();
//    }
    @PutMapping(path="{id}",
            consumes = "application/x-www-form-urlencoded"
    )
    public ResponseEntity<Void> put(Ied ied, @PathVariable Long id){
        System.out.println("id:"+id);
        System.out.println(ied);
        return ResponseEntity.ok().build();
    }
}
