package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Tøída reprezentující solid
 * 
 * @author Milan
 *
 */

public class Solid implements Topology, Geometry {
	private List<Vertex> vertecis = new ArrayList<>();
	private List<Integer> indices = new ArrayList<>();
	private List<Part> parts = new ArrayList<>();

	@Override
	public List<Vertex> getVertices() {
		return vertecis;
	}

	@Override
	public List<Integer> getIndices() {
		return indices;
	}

	@Override
	public List<Part> getPart() {
		return parts;
	}

	public Part getPart(int index) {
		if (index < 0 || index > parts.size())
			throw new IndexOutOfBoundsException();
		return parts.get(index);
	}

	class SolidPart implements Part {
		private int count;
		private int startIndex;
		private PartType partType;

		public SolidPart(int count, int startIndex, PartType partType) {
			this.count = count;
			this.startIndex = startIndex;
			this.partType = partType;
		}

		@Override
		public int getCount() {
			return count;
		}

		@Override
		public int getStartIndex() {
			return startIndex;
		}

		@Override
		public PartType getPartType() {
			return partType;
		}

	}
}
