package delta.soen390.mapsters.Effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Cat on 3/30/2015.
 */
public class EffectManager {

    private HashMap<Object,ArrayList<IEffect>> mEffectMap = new HashMap<>();

    public EffectManager()
    {

    }

    public Collection<IEffect> getEffects(Object reference)
    {
        return mEffectMap.get(reference);
    }

    public void addEffect(Object reference, IEffect effect)
    {
        effect.onStart();
        ArrayList<IEffect> effectList = mEffectMap.get(reference);
        if(effectList == null)
        {
            effectList = new ArrayList<>();
        }
        effectList.add(effect);
       mEffectMap.put(reference,effectList);
    }

    public void update() {
        for (ArrayList<IEffect> effectList : mEffectMap.values()) {
             for(IEffect effect : effectList)
             {
                 effect.onUpdate();
             }
        }
    }

    public void removeEffect(Object reference)
    {
        ArrayList<IEffect> effectList = mEffectMap.get(reference);

        if(effectList == null){
            return;
        }

        for(IEffect effect :effectList)
        {
            effect.onEnd();
        }
        effectList.clear();
    }


}
