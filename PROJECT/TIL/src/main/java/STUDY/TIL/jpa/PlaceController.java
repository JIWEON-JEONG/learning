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
    private final BrandRepository brandRepository;

    //쿼리 3개 발생.
    @GetMapping("place")
    public ResponseEntity<List<Place>> findAll() {
        Brand brand1 = Brand.builder()
                .name("신전 떡볶이")
                .description("이브랜드는 떡이 참 쫄깃해요")
                .build();
        Brand brand2 = Brand.builder()
                .name("조스 떡볶이")
                .description("이브랜드는 매콤해요")
                .build();

        brandRepository.save(brand1);
        brandRepository.save(brand2);

        Place place1 = Place.builder()
                .name("신전 방배점")
                .location("방배2동")
                .build();
        place1.setBrand(brand1);

        Place place2 = Place.builder()
                .name("신전 양재점")
                .location("양재2동")
                .build();
        place2.setBrand(brand1);

        placeRepository.save(place1);
        placeRepository.save(place2);

        System.out.println("----------------");
        List<Place> places = placeRepository.findAll();
        return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
    }

}
