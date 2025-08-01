package org.example.data_structure;


/**
 * HashTable : Array 와 Hash 함수를 사용하여 Map을 구현한 자료구조. 조회 O(1).
 * => HashFunction return 값을 배열의 크기로 Modulo 연산 후 나온 인덱스에 Key 와 함께 Append.
 * Hash 란: 임의의 길이를 고정된 길이로 변환하는 작업.
 *
 * HashTable Resizing: 자바 기준 75% 찼을때, 2배 늘리고 해시값 모듈로 연산 재수행해서 다시 정렬시키는 Flow.
 *
 * 해시충돌: Key가 다른데 Hash 가 같을경우, 혹은 Modulo 연산 한 값이 같을경우 해시충돌이 발생한다.
 * 해결 1 : Value 를 Linked List 로 관리. (Java)
 * 해결 2 : 가장 가까운 비어있는 공간을 활용하고, 조회시에도 근처 공간들 모두 확인해야함. + 삭제시에도 더미값을 넣어줘야함.
 */
public class MapExample {
}
