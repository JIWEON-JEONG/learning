package TIL.java.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Nonserializable 의 문제점 : 해당 객체를 persist 할 이유가 없다. !!!
 */
public class Customize implements Serializable, Runnable {

    transient private Thread animator;
    private int animationSpeed;

    public Customize(int animationSpeed)
    {
        this.animationSpeed = animationSpeed;
        startAnimation();
    }

    private void writeObject(ObjectOutputStream out) throws IOException
    {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        // our "pseudo-constructor"
        in.defaultReadObject();
        // now we are a "live" object again, so let's run rebuild and start
        startAnimation();
    }

    private void startAnimation()
    {
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
