import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit test for Util
 */

public class UtilTest extends Util{

    @Test
    public void istErstesHalbjahr_test(){

        assert (istErstesHalbjahr(6));
        assert (istErstesHalbjahr(1));
        assertFalse(istErstesHalbjahr(7));
        assertFalse(istErstesHalbjahr(12));

        try{
            istErstesHalbjahr(13);
            assert(false);
        }catch(IllegalArgumentException e){
        }

        try{
            istErstesHalbjahr(0);
            assert(false);
        }catch(IllegalArgumentException e){
        }
    }
}