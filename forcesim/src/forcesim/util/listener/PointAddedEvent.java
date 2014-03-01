package forcesim.util.listener;

import java.util.EventObject;

import forcesim.field.physics.IPoint;

public class PointAddedEvent extends EventObject {
	public IPoint point;
	
	public PointAddedEvent(Object source, IPoint point) {
		super(source);
		this.point = point;
	}

}
