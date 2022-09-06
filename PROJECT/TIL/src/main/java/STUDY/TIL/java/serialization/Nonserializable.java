package STUDY.TIL.java.serialization;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 반면에 Thread, OutputStream 및 해당 하위 클래스, Socket과 같은 특정 시스템 수준 클래스는 직렬화할 수 없습니다.
 *
 * transient : 직렬화 하는 과정에서 제외 하고 싶을 경우 사용.
 *
 * 패스워드와 같은 보안정보가 직렬화(Serialize) 과정에서 제외하고 싶은 경우에 적용합니다.
 * 다양한 이유로 데이터를 전송을 하고 싶지 않을 때 선언할 수 있습니다.'
 *
 * Role #2: The object to be persisted must mark all nonserializable fields transient
 */
public class Nonserializable implements Serializable, Runnable {

    transient private Thread animator;
    private int animationSpeed;

    public Nonserializable(int animationSpeed)
    {
        this.animationSpeed = animationSpeed;
        animator = new Thread(this);
        animator.start();
    }

    public void run()
    {
        while(true)
        {
            // do animation here
        }
    }
}
