package org.thenesis.planetino2.game;

import org.thenesis.planetino2.math3D.VoxelMatrixPolygonGroup;
import org.thenesis.planetino2.sound.Music;
import org.thenesis.planetino2.util.Vector;

public class BoxMatrix extends GameObject implements NoisyObject {
	
	private Vector compositeGameObjects;

	public BoxMatrix(VoxelMatrixPolygonGroup polygonGroup) {
		super(polygonGroup, polygonGroup.getBounds());
	}
	
//	//@Override
//	public void update(GameObject player, long elapsedTime) {
//		super.update(player, elapsedTime);
//		BoxPolygonGroup posterPeer = (BoxPolygonGroup)getPolygonGroup();
//		posterPeer.updateImage(elapsedTime);
//	}

	//@Override
	public double getMaxSoundLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	//@Override
	public Music getSoundLoop() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public void setSoundLoop(String name) {
		// TODO Auto-generated method stub
		
	}
	
//	public Vector getCompositeGameObjects() {
//		
//		if (compositeGameObjects == null) {
//			Vector elements = ((VoxelMatrixPolygonGroup)getPolygonGroup()).getElements();
//			int size = elements.size();
//			for (int j = 0; j < size; j++) {
//				GameObject gameObject = new GameObject(polygonGroup);
//				mapObjects.addElement((PolygonGroup) elements.elementAt(j));
//		}
//			compositeGameObjects.
//		}
//		
//		
//	}
	
	

}
