/**
 * Created by berrietrippe on 04/05/2017.
 */
class StupidPackerTest extends PackerTest {

    @Override
    Packer newPacker() {
        return new StupidPacker();
    }
}