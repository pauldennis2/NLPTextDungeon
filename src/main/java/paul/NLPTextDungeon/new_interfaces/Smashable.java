package paul.NLPTextDungeon.new_interfaces;

import paul.NLPTextDungeon.entities.Hero;

/**
 * Created by Paul Dennis on 9/5/2017.
 */
public interface Smashable {

    public boolean smash (Hero hero);
    public void onSmash (Hero hero);
}
