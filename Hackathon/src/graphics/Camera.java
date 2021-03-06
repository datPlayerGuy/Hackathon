package graphics;

import org.joml.Vector3f;
import input.Keyboard;
import misc.Settings;

public class Camera
{
	private final Vector3f position;
	private final Vector3f rotation;
	
	public void update()
	{
		Vector3f cameraInc = new Vector3f(0, 0, 0);
		
        if(Keyboard.getKey(Settings.moveLeft)) 
        {
            cameraInc.x = -Settings.cameraSpeed;
        } else if (Keyboard.getKey(Settings.moveRight)) 
        {
            cameraInc.x = Settings.cameraSpeed;
        }
        if (Keyboard.getKey(Settings.moveDown)) {
            cameraInc.y = -Settings.cameraSpeed;
        } else if (Keyboard.getKey(Settings.moveUp)) 
        {
            cameraInc.y = Settings.cameraSpeed;
        }
        
        movePosition(cameraInc);
	}
	
	public void movePosition(Vector3f movePosition)
	{
		if ( movePosition.z != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * movePosition.z;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * movePosition.z;
        }
        if ( movePosition.x != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * movePosition.x;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * movePosition.x;
        }
        position.y += movePosition.y;
        if(position.y <= 0.0f)
        	position.y = 0.0f;
	}
	
	private float simplifyRotation(float value)
	{
		while(value >= 360.f)
		{
			value -= 360.f;
		}
		while(value <= -360.f)
		{
			value += 360.f;
		}
		
		return value;
	}
	
	public void moveRotation(Vector3f moveRotation)
	{
		rotation.x = simplifyRotation(rotation.x + moveRotation.x);
		rotation.y = simplifyRotation(rotation.y + moveRotation.y);
		rotation.z = simplifyRotation(rotation.z + moveRotation.z);
	}
	
	public void setRotation(Vector3f rotation)
	{
		this.rotation.x = simplifyRotation(rotation.x);
		this.rotation.y = simplifyRotation(rotation.y);
		this.rotation.z = simplifyRotation(rotation.z);
	}
	
	public void setPosition(Vector3f position)
	{
		this.position.x = position.x;
		this.position.y = position.y;
		this.position.z = position.z;
	}
	
	public Vector3f getRotation()
	{
		return rotation;
	}
	
	public Vector3f getPosition()
	{
		return position;
	}
	
	public Camera()
	{
		position = new Vector3f(0, 0, -10);
		rotation = new Vector3f(0, 180, 0);
	}
}
