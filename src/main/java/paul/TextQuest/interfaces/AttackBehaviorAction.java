package paul.NLPTextDungeon.interfaces;

import paul.NLPTextDungeon.entities.Hero;

/**
 * Created by Paul Dennis on 8/13/2017.
 */
public interface AttackBehaviorAction {
    void doAttack(Hero hero, int damage, boolean solved);
}
