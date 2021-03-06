package entities;

import org.joml.Vector3f;
import graphics.Renderer;
import graphics.Sprite;

public class Entity 
{
	protected String id;
	protected Sprite sprite;
	protected float carry;
	protected float carryLimit;
	protected float health;
	protected float damage;
	protected float defence;
	protected float speed;
	protected boolean dead;
	
	protected void attack(Entity entity)
	{
		entity.damage(this.damage);
	}
	
	public float carry(float amount)
	{
		if(amount > carryLimit - carry)
			amount = carryLimit - carry;
		carry += carryLimit;
		return amount;
	}
	
	public void damage(float value)
	{
		value -= defence;
		if(value <= 0)
			return;
		health -= value;
		if(health <= 0)
			die();
	}
	
	public boolean isDead()
	{
		return dead;
	}
	
	private void die()
	{
		dead = true;
	}
	
	public void setPosition(Vector3f position)
	{
		sprite.getModel().setPosition(position);
	}
	
	public Vector3f getPosition()
	{
		return sprite.getModel().getPosition();
	}
	
	protected void moveTowardsEntity(Entity entity)
	{
		float mov = entity.getPosition().x - this.getPosition().x;
		
		if(mov < 0)
		{
			this.move(new Vector3f(-speed, 0, 0));
		}
		else
		{
			this.move(new Vector3f(speed, 0, 0));
		}
	}
	
	public void render()
	{
		Renderer.models.add(sprite.getModel());
	}
	
	public void update()
	{
		
	}
	
	public void move(Vector3f move)
	{
		sprite.getModel().movePosition(move);
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public String getID()
	{
		return id;
	}
	
	public Entity(Sprite sprite, float health, float defence, float damage, float speed)
	{
		id = "null";
		this.sprite = sprite;
		this.health = health;
		this.damage = damage;
		this.defence = defence;
		this.speed = speed;
		dead = false;
		carry = 0.0f;
		carryLimit = 1.0f;
	}
}
