package model;

import java.util.List;

/**
 * Interface reprezentující solid typy
 * 
 * @author Milan
 *
 */
public interface Topology {

	List<Integer> getIndices();

	List<Part> getPart();

	interface Part {
		enum PartType {
			LINE, TRIANGLE, AXIS
		}

		int getCount();

		int getStartIndex();

		PartType getPartType();
	}

}
