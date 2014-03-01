package forcesim.field.physics;

public class ElectromagneticForce {//Coulomb's 
	private static final double COULOMBS_CONSTANT = 8987551787.3681764;
	
	private static double getForceMagnitude(IPoint p1, IPoint p2) {
		double force = 0;
		double r = p1.getDistanceTo(p2);
		double q1 = p1.getCharge();
		double q2 = p2.getCharge();
		
		force = COULOMBS_CONSTANT * q1 * q2 / Math.pow(r, 2);
		
		return force;
	}
	
	public static Vector2D getVector(IPoint p1, IPoint p2) {
		double force = getForceMagnitude(p1, p2);
		double angle = p1.getAngleTo(p2);
		return new Vector2D(force * Math.cos(angle), force * Math.sin(angle));
	}

}
