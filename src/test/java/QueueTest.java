import static org.junit.Assert.assertTrue;

import de.hfu.Queue;
import org.junit.*;

/**
 * Unit test for de.hfu.Queue
 */
public class QueueTest
{
    private Queue testQueue;

    @Before
    public void erzeugeQueue(){
        testQueue = new Queue(3);
    }

    @Test
    public void enqueue_test(){
        testQueue.enqueue(1);
        testQueue.enqueue(2);
        testQueue.enqueue(3);
        testQueue.enqueue(4);
        testQueue.dequeue();
        testQueue.dequeue();
        assert(testQueue.dequeue() == 4);
    }

    @Test
    public void dequeue_test(){
        testQueue.enqueue(1);
        testQueue.enqueue(2);
        testQueue.enqueue(3);
        assert(testQueue.dequeue() == 1);
        assert(testQueue.dequeue() == 2);
        assert(testQueue.dequeue() == 3);
        try{
            testQueue.dequeue();
            assert(false);
        }catch(IllegalStateException e){
        }
    }
}