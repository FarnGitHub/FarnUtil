package farn.farn_util.api.biped_model_extended;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.render.entity.model.BipedEntityModel;

@Deprecated
public class ModelEvents {
    private final ObjectArrayList<Event> events = new ObjectArrayList<>();

    public void iterate(BipedEntityModel model, float limbAngle, float limbDistance,
                        float animationProgress, float headYaw, float headPitch,
                        float scale) {

        if(events.isEmpty()) return;
        for(Event e : events)
            e.accept(model, limbAngle, limbDistance,
                    animationProgress, headYaw, headPitch, scale);
    }

    public void register(Event e) {
        events.add(e);
    }

    public interface Event {

        void accept(BipedEntityModel model, float limbAngle, float limbDistance,
                    float animationProgress, float headYaw, float headPitch, float scale);
    }


}
