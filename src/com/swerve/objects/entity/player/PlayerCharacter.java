package com.swerve.objects.entity.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.chances.api.common.game.entity.entities.BaseRectEntity;
import com.chances.api.common.game.properties.enums.Direction;
import com.swerve.objects.entity.bullet.Bullet;

public class PlayerCharacter extends BaseRectEntity {

	private ArrayList<Bullet> bullets;

	private int currentCooldown = 0;

	private int cooldown = 10;

	private int maxBullets = 20;

	private Boolean isFiring = false;

	/**
	 * Construction
	 */

	public PlayerCharacter() {
		this.bullets = new ArrayList<Bullet>();
		this.entityColor = Color.ORANGE;
	}

	/**
	 * Updating and drawing
	 */

	public void draw(Graphics2D g2d) {
		
		drawPlayerCharacter(g2d);
		drawBullets(g2d);
	}

	private void drawPlayerCharacter(Graphics2D g2d) {
		
		g2d.setColor(entityColor);
		g2d.fill(this);
	}

	private void drawBullets(Graphics2D g2d) {
		for (Bullet b : bullets) {
			b.draw(g2d);
		}
	}

	public void fireBullet() {
		if (bullets.size() < maxBullets && currentCooldown == 0) {
			
			Bullet b = new Bullet();

			b.x = (int) (this.getX() + this.getWidth());
			b.y = (int) this.getCenterY();
			b.setEntityColor(Color.BLUE);
			b.setEntityMovementDirection(Direction.EAST);

			bullets.add(b);
			currentCooldown = cooldown;
		}
	}

	/**
	 * movement and speed
	 */

	private void moveBullets() {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update();
			if (!bullets.get(i).isVisible()) {
				bullets.remove(i);
				i--;
			}
		}
	}

	public Boolean getIsFiring() {
		return isFiring;
	}

	public void setIsFiring(Boolean isFiring) {
		this.isFiring = isFiring;
	}

	public void update() {
		move();
		stopCrossBorder();
		if (isFiring) {
			fireBullet();
		}
		moveBullets();
		if (currentCooldown > 0)
			currentCooldown--;
	}

}
