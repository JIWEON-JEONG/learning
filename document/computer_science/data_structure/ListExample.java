
package org.example.computer_science.data_structure;


import java.util.HashMap;
import java.util.LinkedList;

/**
 * 배열과 달리 크기가 가변적인 자료구조.
 * 장점은 제약없이 데이터를 추가 및 삭제가 가능하다.
 * 리스트는 연속된 메모리 공간에 저장하는 것이 아니기 때문에, 특정 인덱스에 접근하기 위해서는 순서대로 접근 해야한다.
 * => 조회 속도가 떨어진다.
 *
 * 많은 양의 데이터를 순차적으로 접근할 필요가 있다면 배열을, 데이터의 추가 및 삭제가 빈번하게 발생한다면 리스트를 사용하는 것이 바람직하다.
 * ArrayList : 배열로 이루어진 리스트이기 때문에, 추가 및 삭제 시 O(N) shift 가 발생. (맨뒤 , 맨앞 제외) But 인덱스 기반의 조회 가능.
 * LinkedList : 추가는 O(1) 이지만, 삭제나 조회 시 O(N) 이다. Head, tail 정보밖에 없기 때문에.
 */
public class ListExample {
}
