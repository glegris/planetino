package org.thenesis.planetino2.demo;

import org.thenesis.planetino2.ai.Projectile;
import org.thenesis.planetino2.game.GameObject;
import org.thenesis.planetino2.game.Player;
import org.thenesis.planetino2.math3D.PolygonGroup;
import org.thenesis.planetino2.math3D.Vector3D;
import org.thenesis.planetino2.sound.Music;
import org.thenesis.planetino2.sound.Sound;
import org.thenesis.planetino2.sound.SoundManager;

public class ShooterPlayer extends Player {
	private static final float DEFAULT_MAX_ADRENALINE = 100.0F;
	private static final int DEFAULT_MAX_AMMO = 50;
	private int ammo = 0;
	private float adrenaline = DEFAULT_MAX_ADRENALINE;
	private SoundManager soundManager;
	private boolean adrenalineMode = false;
	private boolean zoomViewMode = false;
	private int lifeCount = 3;
	private int kills = 0;
	public boolean goNextLevel = false;

	private Music itemCatchSound;
	private Sound fireSound;
	private Sound jumpSound;

	public ShooterPlayer(SoundManager soundManager) {
		super();

		this.soundManager = soundManager;
	
		jumpSound = soundManager.getSound("jump1.wav");
		itemCatchSound = soundManager.getMusic("power_up2.wav");
		fireSound = soundManager.getSound("hook_fire.wav");

	}

	public int getAmmo() {
		return this.ammo;
	}

	public int getMaxAmmo() {
		return DEFAULT_MAX_AMMO;
	}

	public boolean isAdrenalineMode() {
		return this.adrenalineMode;
	}

	public boolean getZoomView() {
		return this.zoomViewMode;
	}

	public void setZoomView(boolean b) {
		this.zoomViewMode = b;
	}

	public void setAdrenalineMode(boolean enabled) {
		this.adrenalineMode = enabled;
	}

	public void resetPlayer() {
		setHealth(DEFAULT_MAX_HEALTH);
		this.adrenaline = DEFAULT_MAX_ADRENALINE;
	}

	public void cappedHealthAdd(float amount) {
		if (getHealth() < DEFAULT_MAX_HEALTH) {
			this.health += amount;
			if (this.health > DEFAULT_MAX_HEALTH) {
				this.health = DEFAULT_MAX_HEALTH;
			}
		}
	}

	public void cappedAdrenalineAdd(float amount) {
		if (getAdrenaline() < DEFAULT_MAX_ADRENALINE) {
			this.adrenaline += amount;
			if (this.adrenaline > DEFAULT_MAX_ADRENALINE) {
				this.adrenaline = DEFAULT_MAX_ADRENALINE;
			}
		}
	}

	public float getAdrenaline() {
		return this.adrenaline;
	}

	public boolean useAdrenaline(int amount) {
		if (this.adrenaline <= 0.0F) {
			return false;
		}
		float fAmount = amount / DEFAULT_MAX_ADRENALINE;
		this.adrenaline -= fAmount;
		if (this.adrenaline < 0.0F) {
			this.adrenaline = 0.0F;
		}
		return true;
	}

	public float getMaxAdrenaline() {
		return DEFAULT_MAX_ADRENALINE;
	}

	@Override
	public void fireProjectile() {
		if (this.ammo <= 0) {
			return;
		}
		this.ammo -= 1;

		float x = -getTransform().getSinAngleY();
		float z = -getTransform().getCosAngleY();
		float cosX = getTransform().getCosAngleX();
		float sinX = getTransform().getSinAngleX();

		Projectile blast = new Projectile((PolygonGroup) this.blastModel.clone(), new Vector3D(cosX * x, sinX, cosX * z), null, 40, 60);
		//blast.setFromPlayer(true);
		float dist = getBounds().getRadius() + blast.getBounds().getRadius();

		blast.getLocation().setTo(getX() + x * dist, getY() + BULLET_HEIGHT, getZ() + z * dist);

		addSpawn(blast);

		fireSound.play();

		makeNoise(500L);
	}

	@Override
	public void notifyObjectCollision(GameObject obj) {
		if (obj.getPolygonGroup().getName().equalsIgnoreCase("healthPack")) {
			if (!itemCatchSound.isPlaying()) {
				itemCatchSound.rewind();
			}
			itemCatchSound.play(false);
			cappedHealthAdd(50.0F);
			setState(obj, STATE_DESTROYED);
		} else if (obj.getPolygonGroup().getName().equalsIgnoreCase("adrenaline")) {
			itemCatchSound.play(false);
			cappedAdrenalineAdd(50.0F);
			setState(obj, STATE_DESTROYED);
		} else if (obj.getPolygonGroup().getName().equalsIgnoreCase("ammo")) {
			itemCatchSound.play(false);
			this.ammo += 50;
			setState(obj, STATE_DESTROYED);
		} else if (obj.getPolygonGroup().getName().equalsIgnoreCase("GrindCable")) { // weapon
			itemCatchSound.play(false);
			ammo = DEFAULT_MAX_AMMO;
			setState(obj, STATE_DESTROYED);
		} /*else if ((obj.getPolygonGroup().getFilename() != null) && (obj.getPolygonGroup().getFilename().equalsIgnoreCase("goal.obj"))) {
			if (this.hasSyrum) {
				this.sm.play(this.s2);

				this.goNextLevel = true;
			}
		} else if ((obj.getPolygonGroup().getFilename() != null) && (obj.getPolygonGroup().getFilename().equalsIgnoreCase("spike.obj"))) {
			setHealth(0.0F);
		} else if ((obj.getPolygonGroup().getFilename() != null) && (obj.getPolygonGroup().getFilename().equalsIgnoreCase("zombie.obj"))) {
			setHealth(getHealth() - 25.0F);
		}*/
	}

	@Override
	public void setJumping(boolean isJumping) {
		super.setJumping(isJumping);
		if (isJumping) {
			jumpSound.play();
		}
	}
	
	

}
