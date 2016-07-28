package MapEntity;

import Common.Point;

public interface MapEntity {

	Point getLocation();
	Point getPreviousLocation();
	void draw();
}
