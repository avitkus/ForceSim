package forcesim.util.listener;

import java.util.EventListener;

public interface AddPointListener extends EventListener {
	public void addPoint(PointAddedEvent event);
}
