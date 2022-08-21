package STUDY.TIL.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class QueryTest {
    @Autowired
    PlaceRepository placeRepository;

    @BeforeEach
    void init() {
        Brand brand1 = Brand.builder()
                .name("신전 떡볶이")
                .description("이브랜드는 떡이 참 쫄깃해요")
                .build();
        Brand brand2 = Brand.builder()
                .name("조스 떡볶이")
                .description("이브랜드는 매콤해요")
                .build();

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
    }

    @Test
    public void findAll() {
        List<Place> places = placeRepository.findAll();
        for (Place place : places) {
            System.out.println(place);

        }
    }
}
