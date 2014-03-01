package forcesim.util.listener;

import java.util.EventObject;

import forcesim.field.physics.IPoint;

@SuppressWarnings("serial")
public class PointAddedEvent extends EventObject {
	private IPoint point;
	
	public PointAddedEvent(Object source, IPoint point) {
		super(source);
		this.point = point;
	}

	public IPoint getPoint() {
		return point;
	}
}
