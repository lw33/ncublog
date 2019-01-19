package cn.edu.ncu.ncublog.async;

import java.util.List;

public interface EventHandler {

    void doHandler(EventModel eventModel);

    List<EventType> getInteresEventTypes();
}
