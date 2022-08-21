package STUDY.TIL.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceRepository placeRepository;

    @GetMapping("/place")
    public ResponseEntity<List<Place>> findAll() {
        List<Place> places = placeRepository.findAll();
        return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
    }

}
